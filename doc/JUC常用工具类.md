## CountDownLatch、CyclicBarrier、Phaser、Semaphore的区别
### 1、CountDownLatch
1. 初始化一个计数n。
2. 每调用一次countDown()方法，n值减1。
3. 线程A、B...调用await()方法，阻塞线程A、B...。 
4. 当计数值为0时，线程A、B...由 等待阻塞 -> 同步阻塞 -> 就绪态，等待获取处理机运行。

### 2、CyclicBarrier
1. 多个线程互相等待，直到各线程到达同一个同步点，再继续一起执行。
2. CyclicBarrier适用于多个线程有固定的多步需要执行，线程间互相等待，当线程都执行完了，再执行下一步。
3. 与CountDownLatch区别，CyclicBarrier可重用。

### 3、Phaser
1. 它与CountDownLatch和CyclicBarrier类似，都是等待一组线程完成工作后再执行下一步，协调线程的工作。
2. 它支持任务在多个点都进行同步。
3. 与CountDownLatch的区别，Phaser支持动态调整注册任务的数量。

### 4、Semaphore
1. 指定数量的信号量集，信号量可重用。类似pool，只不过Semaphore用于线程同步/异步，且手动调用指定方法获取和释放信号量。
2. 通过acquire()获取一个信号量，release()释放一个信号量。







