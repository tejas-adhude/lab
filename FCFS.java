import java.util.Scanner;

public class FCFS {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\n*.*.program for FCFS cpu Scheduling.*.*\n");
        int n = getNumberOfProcesses(sc);
        int[] process = new int[n];
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];

        inputProcessDetails(sc, n, process, arrivalTime, burstTime);
        performFCFS(n, process, arrivalTime, burstTime);

        sc.close();
    }

    private static int getNumberOfProcesses(Scanner sc) {
        System.out.print("Enter Number of Processes: ");
        return sc.nextInt();
    }

    private static void inputProcessDetails(Scanner sc, int n, int[] process, int[] arrivalTime, int[] burstTime) {
        for (int i = 0; i < n; i++) {
            process[i] = (i + 1);
            System.out.print("\nEnter P" + process[i] + " AT: ");
            arrivalTime[i] = sc.nextInt();
            System.out.print("Enter P" + process[i] + " BT: ");
            burstTime[i] = sc.nextInt();
        }
    }

    private static void performFCFS(int n, int[] process, int[] arrivalTime, int[] burstTime) {
        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];
        float avgTAT = 0;
        float avgWT = 0;

        calculateCompletionTimes(n, arrivalTime, burstTime, completionTime);

        calculateTurnaroundAndWaitingTimes(n, arrivalTime, completionTime, burstTime, turnaroundTime, waitingTime);

        displayFCFSTable(n, process, arrivalTime, burstTime, completionTime, turnaroundTime, waitingTime);

        calculateAverages(n, turnaroundTime, waitingTime, avgTAT, avgWT);
    }

    private static void calculateCompletionTimes(int n, int[] arrivalTime, int[] burstTime, int[] completionTime) {
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                completionTime[i] = arrivalTime[i] + burstTime[i];
            } else {
                if (arrivalTime[i] > completionTime[i - 1]) {
                    completionTime[i] = arrivalTime[i] + burstTime[i];
                } else {
                    completionTime[i] = completionTime[i - 1] + burstTime[i];
                }
            }
        }
    }

    private static void calculateTurnaroundAndWaitingTimes(int n, int[] arrivalTime, int[] completionTime,
            int[] burstTime, int[] turnaroundTime, int[] waitingTime) {
        for (int i = 0; i < n; i++) {
            turnaroundTime[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = turnaroundTime[i] - burstTime[i];
        }
    }

    private static void displayFCFSTable(int n, int[] process, int[] arrivalTime, int[] burstTime,
            int[] completionTime, int[] turnaroundTime, int[] waitingTime) {
        
        System.out.println("\nProcess no\tArrival time\tBrust time\tCompletion Time\tTurn around time\tWaiting time");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + process[i] + "\t\t" + arrivalTime[i] + "ms\t\t" + burstTime[i] + "ms\t\t"
                    + completionTime[i] + "ms\t\t\t" + turnaroundTime[i] + "ms\t\t\t" + waitingTime[i] + "ms");
        }
    }

    private static void calculateAverages(int n, int[] turnaroundTime, int[] waitingTime, float avgTAT, float avgWT) {
        for (int i = 0; i < n; i++) {
            avgTAT += turnaroundTime[i];
            avgWT += waitingTime[i];
        }
        System.out.println("\nAverage turn around time of processor: " + (avgTAT / n)
                + "ms\nAverage waiting time of processor: " + (avgWT / n) + "ms");
    }
}
