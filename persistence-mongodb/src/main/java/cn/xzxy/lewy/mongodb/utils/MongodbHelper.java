package cn.xzxy.lewy.mongodb.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * mongodb curd 工具类
 *
 * @author lewy95
 */
@Component
public class MongodbHelper {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 创建一个集合（一般来说，一个集合中存一种数据类型）
     *
     * @param name 集合名称，相当于传统数据库的表名
     */
    public void createCollection(String name) {
        mongoTemplate.createCollection(name);
    }

    /**
     * 创建索引（顺序且唯一）
     *
     * @param collectionName 集合名称，相当于关系型数据库中的表名
     * @param filedName      对象中的某个属性名
     * @return index         索引名称
     */
    public String createIndex(String collectionName, String filedName) {
        //配置索引选项
        IndexOptions options = new IndexOptions();
        // 设置为唯一
        options.unique(true);
        //创建按filedName升序排的索引
        return mongoTemplate.getCollection(collectionName).createIndex(Indexes.ascending(filedName), options);
    }

    /**
     * 获取当前集合对应的所有索引的名称
     *
     * @param collectionName 集合名
     * @return list 索引名称
     */
    public List<String> getAllIndexes(String collectionName) {
        ListIndexesIterable<Document> list = mongoTemplate.getCollection(collectionName).listIndexes();
        List<String> indexes = new ArrayList<>();
        for (Document document : list) {
            document.forEach((key1, value) -> {
                //提取出索引的名称
                if (key1.equals("name")) {
                    indexes.add(value.toString());
                }
            });
        }
        return indexes;
    }

    /**
     * 指定集合保存数据对象
     *
     * @param obj            数据对象
     * @param collectionName 集合名
     */
    public Object save(Object obj, String collectionName) {

        return mongoTemplate.save(obj, collectionName);
    }

    /**
     * 指定集合保存数据对象
     *
     * @param obj            数据对象
     * @param collectionName 集合名
     */
    public void insert(Object obj, String collectionName) {

        mongoTemplate.insert(obj, collectionName);
    }

    /**
     * 往对应的集合中批量插入数据，注意批量的数据中不要包含重复的id
     *
     * @param infos 对象列表
     */
    public void insertMulti(List<?> infos, String collectionName) {

        mongoTemplate.insert(infos, collectionName);
    }

    /**
     * 指定集合 根据数据对象中的id删除数据
     *
     * @param id             _id 对应的value
     * @param collectionName 集合名
     */
    public void removeById(String id, String collectionName) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in((Object) convertObjectId(id)));
        mongoTemplate.remove(query, collectionName);
    }

    /**
     * 根据key，value到指定集合删除数据
     *
     * @param keys            键
     * @param values          值
     * @param collectionName 集合名
     */
    public void removeByKey(String[] keys, Object[] values, String collectionName) {

        Criteria criteria = null;
        for (int i = 0; i < keys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(keys[i]).is(values[i]);
            } else {
                criteria.and(keys[i]).is(values[i]);
            }
        }
        Query query = Query.query(criteria);
        mongoTemplate.remove(query, collectionName);
    }

    /**
     * 指定集合 删除集合中所有数据
     *
     * @param collectionName 集合名
     */
    public void removeAll(String collectionName) {
        Query query = new Query();
        mongoTemplate.remove(query, collectionName);
    }

    /**
     * 指定集合 修改数据，且仅修改找到的第一条数据
     *
     * @param accordingKey   修改条件 key
     * @param accordingValue 修改条件 value
     * @param updateKeys     修改内容 key数组
     * @param updateValues   修改内容 value数组
     * @param collectionName 集合名
     */
    public void updateFirst(String accordingKey,
                            Object accordingValue,
                            String[] updateKeys,
                            Object[] updateValues,
                            String collectionName) {

        Criteria criteria = Criteria.where(accordingKey).is(accordingValue);
        Query query = Query.query(criteria);
        Update update = new Update();
        for (int i = 0; i < updateKeys.length; i++) {
            update.set(updateKeys[i], updateValues[i]);
        }
        mongoTemplate.updateFirst(query, update, collectionName);
    }

    /**
     * 指定集合 修改数据，且修改所找到的所有数据
     *
     * @param accordingKey   修改条件 key
     * @param accordingValue 修改条件 value
     * @param updateKeys     修改内容 key数组
     * @param updateValues   修改内容 value数组
     * @param collectionName 集合名
     */
    public void updateMulti(String accordingKey,
                            Object accordingValue,
                            String[] updateKeys,
                            Object[] updateValues,
                            String collectionName) {

        Criteria criteria = Criteria.where(accordingKey).is(accordingValue);
        Query query = Query.query(criteria);
        Update update = new Update();
        for (int i = 0; i < updateKeys.length; i++) {
            update.set(updateKeys[i], updateValues[i]);
        }
        mongoTemplate.updateMulti(query, update, collectionName);
    }

    /**
     * 使用主键索引信息精确更改某条数据
     *
     * @param primaryKey     主键
     * @param primaryValue   主键值
     * @param collectionName 集合名称
     * @param info           待更新的内容
     */
    public void updateByPrimaryKey(String primaryKey,
                                   Object primaryValue,
                                   String collectionName,
                                   Object info) {

        Query query = new Query(Criteria.where(primaryKey).is(primaryValue));
        Update update = new Update();
        String str = JSON.toJSONString(info);
        JSONObject jsonObject = JSON.parseObject(str);
        jsonObject.forEach((key, value) -> {
            //类似传统数据库中的主键，不更新
            if (!key.equals(primaryKey)) {
                update.set(key, value);
            }
        });
        mongoTemplate.updateMulti(query, update, info.getClass(), collectionName);
    }

    /**
     * 指定集合 根据条件查询出所有结果集
     *
     * @param obj            数据对象
     * @param findKeys       查询条件 key
     * @param findValues     查询条件 value
     * @param collectionName 集合名
     * @return list
     */
    public List<?> find(Object obj,
                        String[] findKeys,
                        Object[] findValues,
                        String collectionName) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        return mongoTemplate.find(query, obj.getClass(), collectionName);
    }

    public List<?> findByRegex(Object obj,
                               String[] findKeys,
                               Object[] findValues,
                               String[] regexKeys,
                               String[] regexValues,
                               String collectionName) {

        if (findKeys.length != findValues.length) {
            throw new RuntimeException("查询条件个数不匹配");
        }

        if (regexKeys.length != regexValues.length) {
            throw new RuntimeException("正则表达式条件个数不匹配");
        }

        Criteria criteria = null;

        if (findKeys.length > 0) {
            for (int i = 0; i < findKeys.length; i++) {
                if (i == 0) {
                    criteria = Criteria.where(findKeys[i]).is(findValues[i]);
                } else {
                    criteria.and(findKeys[i]).is(findValues[i]);
                }
            }

            if (regexKeys.length > 0) {
                for (int j = 0; j < regexKeys.length; j++) {
                    criteria.and(regexKeys[j]).regex(regexValues[j]);
                }
            }
        } else {
            if (regexKeys.length > 0) {
                for (int k = 0; k < regexKeys.length; k++) {
                    if (k == 0) {
                        criteria = Criteria.where(regexKeys[k]).regex(regexValues[k]);
                    } else {
                        criteria.and(regexKeys[k]).is(regexValues[k]);
                    }
                }
            }
        }

        Query query = Query.query(criteria);
        return mongoTemplate.find(query, obj.getClass(), collectionName);
    }

    /**
     * 指定集合 根据条件查询出所有结果集 并排倒序
     *
     * @param obj            数据对象
     * @param findKeys       查询条件 key
     * @param findValues     查询条件 value
     * @param collectionName 集合名
     * @param sort           排序字段
     * @return list
     */
    public List<?> findBySort(Object obj,
                              String[] findKeys,
                              Object[] findValues,
                              String collectionName,
                              String sort,
                              int sortFlag) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }

        Query query = Query.query(criteria);
        if (sortFlag == 1) {
            query.with(Sort.by(Direction.ASC, sort));
        } else {
            query.with(Sort.by(Direction.DESC, sort));
        }
        return mongoTemplate.find(query, obj.getClass(), collectionName);
    }

    /**
     * 指定集合 根据条件查询出符合的第一条数据
     *
     * @param obj            数据对象
     * @param findKeys       查询条件 key
     * @param findValues     查询条件 value
     * @param collectionName 集合名
     * @return object
     */
    public Object findOne(Object obj, String[] findKeys, Object[] findValues, String collectionName) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        return mongoTemplate.findOne(query, obj.getClass(), collectionName);
    }

    /**
     * 指定集合 根据条件查询出符合的第一条数据
     *
     * @param obj            数据对象
     * @param findKeys       查询条件 key
     * @param findValues     查询条件 value
     * @param collectionName 集合名
     */
    public void findAndModify(Object obj,
                              String[] findKeys,
                              Object[] findValues,
                              String[] targetKeys,
                              Object[] targetValues,
                              String collectionName) {

        if (findKeys.length != findValues.length) {
            throw new RuntimeException("查询的key和value个数不对应");
        }

        if (targetKeys.length != targetValues.length) {
            throw new RuntimeException("要更新的key和value个数不对应");
        }

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        Update update = new Update();

        for (int j = 0; j < targetKeys.length; j++) {
            update.set(targetKeys[j], targetValues[j]);
        }

        mongoTemplate.findAndModify(query, update, obj.getClass(), collectionName);
    }


    /**
     * 指定集合 查询出所有结果集
     *
     * @param obj            数据对象
     * @param collectionName 集合名
     * @return list
     */
    public List<?> findAll(Object obj, String collectionName) {

        return mongoTemplate.findAll(obj.getClass(), collectionName);
    }

    // --------------基于 @Document 注解生成的api-------------------

    /**
     * 保存数据对象，集合为数据对象中 @Document 注解所配置的collection
     *
     * @param obj 数据对象
     */
    public Object save(Object obj) {

        return mongoTemplate.save(obj);
    }

    /**
     * 根据数据对象中的id删除数据，集合为数据对象中 @Document 注解所配置的collection
     *
     * @param obj 数据对象
     */
    public void remove(Object obj) {
        mongoTemplate.remove(obj);
    }

    /**
     * 根据条件查询出所有结果集 集合为数据对象中 @Document 注解所配置的collection
     *
     * @param obj        数据对象
     * @param findKeys   查询条件 key
     * @param findValues 查询条件 value
     * @return list
     */
    public List<?> find(Object obj, String[] findKeys, Object[] findValues) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        return mongoTemplate.find(query, obj.getClass());
    }

    /**
     * 根据条件查询出符合的第一条数据 集合为数据对象中 @Document 注解所配置的collection
     *
     * @param obj        数据对象
     * @param findKeys   查询条件 key
     * @param findValues 查询条件 value
     * @return object
     */
    public Object findOne(Object obj, String[] findKeys, Object[] findValues) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        return mongoTemplate.findOne(query, obj.getClass());
    }

    /**
     * 查询出所有结果集 集合为数据对象中 @Document 注解所配置的collection
     *
     * @param obj 数据对象
     * @return list
     */
    public List<?> findAll(Object obj) {
        return mongoTemplate.findAll(obj.getClass());
    }

    // --------------------- 通用转换-------------------------------
    /**
     * 转换为ObjectId
     *
     * @param ids 多id
     * @return ObjectId[]
     */
    private ObjectId[] convertObjectId(String... ids) {
        if (ids == null) {
            return new ObjectId[0];
        }
        ObjectId[] objectIds = new ObjectId[ids.length];

        if (ids.length == 0) {
            return objectIds;
        }

        for (int i = 0; i < ids.length; i++) {
            objectIds[i] = new ObjectId(ids[i]);
        }
        return objectIds;
    }
}