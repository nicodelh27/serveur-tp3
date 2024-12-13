class ScalarProductThread extends Thread {
    private double[] v1;       // Premier vecteur
    private double[] v2;       // Deuxième vecteur
    private int start, end;    // Plage d'indices à traiter
    private static double result = 0; // Résultat global partagé
    private static final Object lock = new Object(); // Verrou pour la synchronisation

    public ScalarProductThread(double[] v1, double[] v2, int start, int end) {
        this.v1 = v1;
        this.v2 = v2;
        this.start = start;
        this.end = end;
    }

    public void run() {
        double partialSum = 0;
        for (int i = start; i < end; i++) {
            partialSum += v1[i] * v2[i];
        }

        // Ajouter la somme partielle au résultat global de manière synchronisée
        synchronized (lock) {
            result += partialSum;
        }
    }

    public static double getResult() {
        return result;
    }
}

class ScalarProduct {
    public static void main(String[] args) {
        // Exemple de vecteurs
        double[] v1 = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
        double[] v2 = {8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0};
        int length = v1.length;

        // Nombre de threads
        int numThreads = 4;
        int chunkSize = (int) Math.ceil((double) length / numThreads);



        // Créer et démarrer les threads
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, length);
            threads[i] = new ScalarProductThread(v1, v2, start, end);
            threads[i].start();
        }

        // Attendre que tous les threads terminent
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Afficher le résultat final
        System.out.println("Produit scalaire : " + ScalarProductThread.getResult());
    }
}
