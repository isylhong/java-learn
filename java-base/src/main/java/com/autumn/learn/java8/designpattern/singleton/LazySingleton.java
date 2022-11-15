package com.autumn.learn.java8.designpattern.singleton;

public class LazySingleton {

    private String str;
    // volatile关键字作用：禁止指令重排，保证返回的instance对象一定在创立对象之后
    private static volatile LazySingleton instance = null;

    private LazySingleton() {
        this.str = "hello world!";
    }

    public String getStr() {
        return str;
    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    // volatile关键字作用为禁止指令重排，保证返回的instance一定在创建对象之后
                    instance = new LazySingleton();
                    // instance = new LazySingleton();语句为非原子性，实际上会执行以下内容
                    // （1）在堆上开辟空间；（2）属性初始化；（3）引用指向对象
                    // 假设以上三个内容为三条单独指令，因指令重排可能会导致执行顺序为1->3->2(正常为1->2->3),当单例模式中存在普通变量需要在构造方法中进行初始化操作时，单线程情况下，顺序重排没有影响；但在多线程情况下，假如线程1执行singleton=new Singleton()语句时先1再3，由于系统调度线程2的原因没来得及执行步骤2，但此时已有引用指向对象也就是singleton!=null，故线程2在第一次检查时不满足条件直接返回singleton，此时singleton为null(即str值为null)
                    // volatile关键字可保证singleton=new LazySingleton();语句执行顺序为1->2->3，因其为非原子性依旧可能存在系统调度问题(即执行步骤时被打断)，但能确保的是只要singleton!=0，就表明一定执行了属性初始化操作；而若在步骤3之前被打断，此时singleton依旧为null，其他线程可进入第一层检查向下执行创建对象

                }
            }
        }
        return instance;
    }


}
