import java.util.ArrayList;
import java.util.List;

class Worker extends Thread {
    private int[] array; // Tableau à traiter
    private int start, end; // Indices de début et de fin
    private int max; // Maximum local
    private int maxIndex; // Position du maximum local

    public Worker(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.max = Integer.MIN_VALUE;
        this.maxIndex = -1;
    }

    public void run() {
        for (int i = start; i < end; i++) {
            if (array[i] > max) {
                max = array[i];
                maxIndex = i;
            }
        }
    }

    public int getMax() {
        return max;
    }

    public int getMaxIndex() {
        return maxIndex;
    }
}

class MaxFinder {
    public static void main(String[] args) {
        // Exemple de tableau à analyser
        int[] array = {3, 15, 7, 9, 12, 21, 18, 30, 5, 10};
        int numThreads = 4; // Nombre de threads
        int length = array.length;

        // Liste des threads et divisions du tableau
        List<Worker> workers = new ArrayList<>();
        int segmentSize = length / numThreads;

        // Créer et démarrer les threads
        for (int i = 0; i < numThreads; i++) {
            int start = i * segmentSize;
            int end = Math.min(start + segmentSize, length);
            Worker worker = new Worker(array, start, end);
            workers.add(worker);
            worker.start();
        }

        // Attendre que tous les threads terminent
        int globalMax = Integer.MIN_VALUE;
        int globalMaxIndex = -1;

        for (Worker worker : workers) {
            try {
                worker.join();
                // Comparer les maximums locaux pour trouver le maximum global
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Worker worker : workers) {
            if (worker.getMax() > globalMax) {
                globalMax = worker.getMax();
                globalMaxIndex = worker.getMaxIndex();
            }
        }

        // Afficher le résultat
        System.out.println("Valeur maximum : " + globalMax);
        System.out.println("Position du maximum : " + globalMaxIndex);
    }
}
