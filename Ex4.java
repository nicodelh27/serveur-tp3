class AdderThread extends Thread {
    private double[] v1;       // Premier vecteur
    private double[] v2;       // Deuxième vecteur
    private double[] result;   // Vecteur résultat
    private int start, end;    // Plage d'indices à traiter

    public AdderThread(double[] v1, double[] v2, double[] result, int start, int end) {
        this.v1 = v1;
        this.v2 = v2;
        this.result = result;
        this.start = start;
        this.end = end;
    }

    public void run() {
        for (int i = start; i < end; i++) {
            result[i] = v1[i] + v2[i]; // Addition élément par élément
        }
    }
}

class VectorAddition {
    public static void main(String[] args) {
        // Exemple de vecteurs
        double[] v1 = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
        double[] v2 = {8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0};
        int length = v1.length;

        // Initialiser le vecteur résultat
        double[] result = new double[length];

        // Nombre de threads
        int numThreads = 4;
        int chunkSize = (int) Math.ceil((double) length / numThreads);

        // Créer et démarrer les threads
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, length);
            threads[i] = new AdderThread(v1, v2, result, start, end);
            threads[i].start();
        }

        // Attendre la fin de tous les threads
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Afficher le vecteur résultat
        System.out.println("Résultat de l'addition :");
        for (double value : result) {
            System.out.print(value + " ");
        }
    }
}
