class Action1 extends Thread {
    static final int n = 5000;
    public void run() {
        for (int i =0; i<n; i++) {
            Ex1.x++;
        }
    }
}

class Ex1 {
    static int x = 0;

    public static void main(String[] args) {
        Thread t1 = new Action1();
        Thread t2 = new Action1();
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

