package cn.xzxy.lewy.redisson.common.service;

import org.redisson.api.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * RedissonClient 封装类
 * 封装后效果和RedissonClient差不多，没什么意义，重点在了解各个方法怎么使用
 * 参考：https://github.com/redisson/redisson/wiki/11.-Redis-commands-mapping
 *
 * @author lewy95
 */
@Service
public class RedissonService {

    @Resource
    private RedissonClient redissonClient;

    // *** module 1 ==== 分布式集合 ***

    /**
     * 获取字符串对象
     *
     * @param objectName key
     * @return RBucket<T>
     */
    public <T> RBucket<T> getRBucket(String objectName) {
        // RBucket<T> bucket = redissonClient.getBucket(objectName);
        return redissonClient.getBucket(objectName);
    }

    /**
     * 获取Map对象
     *
     * @param objectName key
     * @return RMap<K, V>
     */
    public <K, V> RMap<K, V> getRMap(String objectName) {
        // RMap<K, V> map = redissonClient.getMap(objectName);
        return redissonClient.getMap(objectName);
    }

    /**
     * 获取多值Map对象（基于List）
     *
     * @param objectName key
     * @return RMap<K, V>
     */
    public <K, V> RMultimap<K, V> getListMultimap(String objectName) {
        // RMultimap<K, V> rMultimap = redissonClient.getListMultimap(objectName);
        return redissonClient.getListMultimap(objectName);
    }

    /**
     * 获取多值Map对象（基于Set）
     *
     * @param objectName key
     * @return RMap<K, V>
     */
    public <K, V> RMultimap<K, V> getSetMultimap(String objectName) {
        // RMultimap<K, V> rMultimap = redissonClient.getSetMultimap(objectName);
        return redissonClient.getSetMultimap(objectName);
    }

    /**
     * 获取有序集合
     *
     * @param objectName key
     * @return RSortedSet<V>
     */
    public <V> RSortedSet<V> getRSortedSet(String objectName) {
        // RSortedSet<V> sortedSet = redissonClient.getSortedSet(objectName);
        return redissonClient.getSortedSet(objectName);
    }

    /**
     * 获取字典排序集合
     *
     * @param objectName key
     * @return RLexSortedSet
     */
    public RLexSortedSet getLexSortedSet(String objectName) {
        // RLexSortedSet sortedSet = redissonClient.getLexSortedSet(objectName);
        return redissonClient.getLexSortedSet(objectName);
    }

    /**
     * 获取计分排序集合
     *
     * @param objectName key
     * @return RScoredSortedSet
     */
    public <V> RScoredSortedSet<V> getScoredSortedSet(String objectName) {
        // RScoredSortedSet scoredSortedSet = redissonClient.getScoredSortedSet(objectName);
        return redissonClient.getScoredSortedSet(objectName);
    }

    /**
     * 获取集合
     *
     * @param objectName key
     * @return RSet<V>
     */
    public <V> RSet<V> getRSet(String objectName) {
        // RSet<V> rSet = redissonClient.getSet(objectName);
        return redissonClient.getSet(objectName);
    }

    /**
     * 获取位集合
     *
     * @param objectName key
     * @return RBitSet
     */
    public RBitSet getRBitSet(String objectName) {
        // RBitSet bitSet = redissonClient.getBitSet(objectName);
        return redissonClient.getBitSet(objectName);
    }

    /**
     * 获取列表
     *
     * @param objectName key
     * @return RList<V>
     */
    public <V> RList<V> getRList(String objectName) {
        // RList<V> rList = redissonClient.getList(objectName);
        return redissonClient.getList(objectName);
    }

    /**
     * 获取队列
     *
     * @param objectName key
     * @return RQueue<V>
     */
    public <V> RQueue<V> getRQueue(String objectName) {
        // RQueue<V> rQueue = redissonClient.getQueue(objectName);
        return redissonClient.getQueue(objectName);
    }

    /**
     * 获取双端队列
     *
     * @param objectName key
     * @return RDeque<V>
     */
    public <V> RDeque<V> getRDeque(String objectName) {
        // RDeque<V> rDeque = redissonClient.getDeque(objectName);
        return redissonClient.getDeque(objectName);
    }

    /**
     * 获取阻塞队列
     *
     * @param objectName key
     * @return RBlockingDeque<V>
     */
    public <V> RBlockingQueue<V> getRBlockingQueue(String objectName) {
        // RBlockingQueue<V> rBlockingQueue = redissonClient.getBlockingQueue(objectName);
        return redissonClient.getBlockingQueue(objectName);
    }

    /**
     * 获取阻塞双端队列
     *
     * @param objectName key
     * @return RBlockingDeque<V>
     */
    public <V> RBlockingDeque<V> getRBlockingDeque(String objectName) {
        // RDeque<V> rDeque = redissonClient.getBlockingDeque(objectName);
        return redissonClient.getBlockingDeque(objectName);
    }

    /**
     * 获取有界阻塞队列
     *
     * @param objectName key
     * @return RBlockingDeque<V>
     */
    public <V> RBoundedBlockingQueue<V> getBoundedBlockingQueue(String objectName) {
        // RBlockingDeque<V> rBlockingDeque = redissonClient.getBoundedBlockingQueue(objectName);
        return redissonClient.getBoundedBlockingQueue(objectName);
    }

    /**
     * 获取延迟队列
     *
     * @param rQueue rQueue
     * @return RDelayedQueue<V>
     */
    public <V> RDelayedQueue<V> getDelayedQueue(RQueue<V> rQueue) {
        // RDelayedQueue<V> rDelayedQueue = redissonClient.getDelayedQueue(objectName);
        return redissonClient.getDelayedQueue(rQueue);
    }

    /**
     * 获取优先队列
     *
     * @param objectName key
     * @return RPriorityQueue<V>
     */
    public <V> RPriorityQueue<V> getPriorityQueue(String objectName) {
        // RPriorityQueue<V> rPriorityQueue = redissonClient.getPriorityQueue(objectName);
        return redissonClient.getPriorityQueue(objectName);
    }

    /**
     * 获取阻塞优先队列
     *
     * @param objectName key
     * @return RPriorityBlockingQueue<V>
     */
    public <V> RPriorityBlockingQueue<V> getPriorityBlockingQueue(String objectName) {
        // RPriorityBlockingQueue<V> rPriorityBlockingQueue = redissonClient.getPriorityBlockingQueue(objectName);
        return redissonClient.getPriorityBlockingQueue(objectName);
    }

    /**
     * 获取优先双端队列
     *
     * @param objectName key
     * @return RPriorityDeque<V>
     */
    public <V> RPriorityDeque<V> getPriorityDeque(String objectName) {
        // RPriorityDeque<V> rPriorityDeque = redissonClient.getPriorityDeque(objectName);
        return redissonClient.getPriorityDeque(objectName);
    }

    /**
     * 获取阻塞优先双端队列
     *
     * @param objectName key
     * @return RPriorityBlockingDeque<V>
     */
    public <V> RPriorityBlockingDeque<V> getPriorityBlockingDeque(String objectName) {
        // RPriorityBlockingDeque<V> rPriorityBlockingDeque = redissonClient.getPriorityBlockingDeque(objectName);
        return redissonClient.getPriorityBlockingDeque(objectName);
    }


    // *** module 2 ==== 分布式对象 ***

    /**
     * 获取基数估计算法
     *
     * @param objectName key
     * @return RHyperLogLog<V>
     */
    public <V> RHyperLogLog<V> getHyperLogLog(String objectName) {
        // RHyperLogLog<V> rHyperLogLog = redissonClient.getHyperLogLog(objectName);
        return redissonClient.getHyperLogLog(objectName);
    }

    /**
     * 获取地理位置信息
     *
     * @param objectName key
     * @return RGeo<V>
     */
    public <V> RGeo<V> getGeo(String objectName) {
        // RGeo<V> rGeo = redissonClient.getGeo(objectName);
        return redissonClient.getGeo(objectName);
    }

    /**
     * 获取布隆过滤器
     *
     * @param objectName key
     * @return RBloomFilter<V>
     */
    public <V> RBloomFilter<V> getBloomFilter(String objectName) {
        // RBloomFilter<V> bloomFilter = redissonClient.getBloomFilter(objectName);
        return redissonClient.getBloomFilter(objectName);
    }

    /**
     * 获取消息的Topic（用于订阅分发）
     *
     * @param objectName key
     * @return RTopic
     */
    public RTopic getRTopic(String objectName) {
        return redissonClient.getTopic(objectName);
    }

    /**
     * 获取二进制流
     *
     * @param objectName key
     * @return RBinaryStream
     */
    public RBinaryStream getBinaryStream(String objectName) {
        // RBinaryStream rBinaryStream = redissonClient.getBinaryStream(objectName);
        return redissonClient.getBinaryStream(objectName);
    }


    /**
     * 获取 Stream (用于MQ)
     *
     * @param objectName key
     * @return RStream<V>
     */
    public RStream getStream(String objectName) {
        // RStream rStream = redissonClient.getStream(objectName);
        return redissonClient.getStream(objectName);
    }

    /**
     * 获取执行的lua脚本
     *
     * @return RHyperLogLog<V>
     */
    public RScript getScript() {
        // RHyperLogLog<V> rHyperLogLog = redissonClient.getScript();
        return redissonClient.getScript();
    }

    /**
     * 获取原子数
     *
     * @param objectName key
     * @return RAtomicLong
     */
    public RAtomicLong getRAtomicLong(String objectName) {
        // RAtomicLong rAtomicLong = redissonClient.getAtomicLong(objectName);
        return redissonClient.getAtomicLong(objectName);
    }

    /**
     * 获取原子数（Double）
     *
     * @param objectName key
     * @return RAtomicDouble
     */
    public RAtomicDouble getRAtomicDouble(String objectName) {
        // RAtomicLong rAtomicLong = redissonClient.getAtomicDouble(objectName);
        return redissonClient.getAtomicDouble(objectName);
    }

    /**
     * 获取整长型累加器
     *
     * @param objectName key
     * @return RLongAdder
     */
    public RLongAdder getLongAdder(String objectName) {
        // RLongAdder rLongAdder = redissonClient.getLongAdder(objectName);
        return redissonClient.getLongAdder(objectName);
    }

    /**
     * 获取双精度浮点累加器
     *
     * @param objectName key
     * @return RDoubleAdder
     */
    public RDoubleAdder getDoubleAdder(String objectName) {
        // RDoubleAdder rDoubleAdder = redissonClient.getDoubleAdder(objectName);
        return redissonClient.getDoubleAdder(objectName);
    }

    /**
     * 获取限流器
     *
     * @param objectName key
     * @return RRateLimiter
     */
    public RRateLimiter getRateLimiter(String objectName) {
        // RRateLimiter rRateLimiter = redissonClient.getRateLimiter(objectName);
        return redissonClient.getRateLimiter(objectName);
    }

    // *** module 8 ==== 分布式锁（Lock）和同步器（Synchronizer） ***

    /**
     * 获取锁
     *
     * @param objectName key
     * @return RLock
     */
    public RLock getRLock(String objectName) {
        // RLock rLock = redissonClient.getLock(objectName);
        return redissonClient.getLock(objectName);
    }

    /**
     * 获取公平锁
     *
     * @param objectName key
     * @return RLock
     */
    public RLock getFairLock(String objectName) {
        // RLock rLock = redissonClient.getFairLock(objectName);
        return redissonClient.getFairLock(objectName);
    }

    /**
     * 获取红锁
     *
     * @param rLock lock
     * @return RLock
     */
    public RLock getRedLock(RLock rLock) {
        // RLock rLock = redissonClient.getRedLock(objectName);
        return redissonClient.getRedLock(rLock);
    }

    /**
     * 获取联锁
     *
     * @param rLock lock
     * @return RLock
     */
    public RLock getMultiLock(RLock rLock) {
        // RLock rLock = redissonClient.getMultiLock(objectName);
        return redissonClient.getMultiLock(rLock);
    }

    /**
     * 获取读写锁
     *
     * @param objectName key
     * @return RReadWriteLock
     */
    public RReadWriteLock getReadWriteLock(String objectName) {
        // RReadWriteLock rwlock = redissonClient.getReadWriteLock(objectName);
        return redissonClient.getReadWriteLock(objectName);
    }

    /**
     * 获取闭锁
     *
     * @param objectName key
     * @return RCountDownLatch
     */
    public RCountDownLatch getRCountDownLatch(String objectName) {
        // RCountDownLatch rCountDownLatch = redissonClient.getCountDownLatch(objectName);
        return redissonClient.getCountDownLatch(objectName);
    }

    /**
     * 获取信号量
     *
     * @param objectName key
     * @return RSemaphore
     */
    public RSemaphore getRSemaphore(String objectName) {
        // RSemaphore rSemaphore = redissonClient.getSemaphore(objectName);
        return redissonClient.getSemaphore(objectName);
    }

    /**
     * 获取可过期信号量
     *
     * @param objectName key
     * @return RPermitExpirableSemaphore
     */
    public RPermitExpirableSemaphore getRPermitExpirableSemaphore(String objectName) {
        // RPermitExpirableSemaphore rPermitExpirableSemaphore = redissonClient.getPermitExpirableSemaphore(objectName);
        return redissonClient.getPermitExpirableSemaphore(objectName);
    }
}
