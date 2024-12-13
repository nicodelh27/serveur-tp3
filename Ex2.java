class Action2 extends Thread {
    static final int n = 50000;
    public void run() {
        for (int i =0; i<n; i++) {
            incr();
        }
    }

    public static synchronized void incr() {
        Ex2.x++;
    }
}

class Ex2 {
    static int x = 0;

    public static void main(String[] args) {
        Thread t1 = new Action2();
        Thread t2 = new Action2();
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(x);
    }
}