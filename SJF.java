import java.util.Scanner;

public class SJF {

    static void inputProcesses(int n, int[] process, int[] arrivalTime, int[] burstTime, int[] remBurstTime, int[] visit, Scanner sc) {
        for (int i = 0; i < n; i++) {
            System.out.println(" ");
            process[i] = (i + 1);
            System.out.print("Enter P" + (i + 1) + " AT: ");
            arrivalTime[i] = sc.nextInt();
            System.out.print("Enter P" + (i + 1) + " BT: ");
            burstTime[i] = sc.nextInt();
            remBurstTime[i] = burstTime[i];
            visit[i] = 0;
        }
    }

    static void sortProcesses(int n, int[] process, int[] arrivalTime, int[] burstTime, int[] remBurstTime) {
        int temp;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (arrivalTime[i] < arrivalTime[j]) {
                    temp = process[j];
                    process[j] = process[i];
                    process[i] = temp;
                    temp = arrivalTime[j];
                    arrivalTime[j] = arrivalTime[i];
                    arrivalTime[i] = temp;
                    temp = remBurstTime[j];
                    remBurstTime[j] = remBurstTime[i];
                    remBurstTime[i] = temp;
                    temp = burstTime[j];
                    burstTime[j] = burstTime[i];
                    burstTime[i] = temp;
                }
            }
        }
    }

    static void shortestJobFirst(int n, int[] arrivalTime, int[] burstTime, int[] remBurstTime, int[] visit, int[] completionTime) {
        int start = 0, total = 0;
        while (true) {
            int min = 99, c = n;
            if (total == n) {
                break;
            }
            for (int i = 0; i < n; i++) {
                if ((arrivalTime[i] <= start) && (visit[i] == 0) && (burstTime[i] < min)) {
                    min = burstTime[i];
                    c = i;
                }
            }

            if (c == n)
                start++;
            else {
                burstTime[c]--;
                start++;
                if (burstTime[c] == 0) {
                    completionTime[c] = start;
                    visit[c] = 1;
                    total++;
                }
            }
        }
    }

    static void calculateTimes(int n, int[] arrivalTime, int[] remBurstTime, int[] completionTime, int[] TAT, int[] waitingTime) {
        for (int i = 0; i < n; i++) {
            TAT[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = TAT[i] - remBurstTime[i];
        }
    }

    static void displayProcesses(int n, int[] process, int[] arrivalTime, int[] remBurstTime, int[] completionTime, int[] TAT, int[] waitingTime) {
        System.out.println("\nProcessor\tArrival time\tBrust time\tCompletion Time\t\tTurn around time\tWaiting time");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + process[i] + "\t\t" + arrivalTime[i] + "ms\t\t" + remBurstTime[i] + "ms\t\t"
                    + completionTime[i] + "ms\t\t\t" + TAT[i] + "ms\t\t\t" + waitingTime[i] + "ms");
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n*.*.program for SJF cpu Scheduling.*.*\n");
        System.out.print("Enter no of process:");
        int n = sc.nextInt();
        int[] process = new int[n];
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] TAT = new int[n];
        int[] waitingTime = new int[n];
        int[] visit = new int[n];
        int[] remBurstTime = new int[n];

        inputProcesses(n, process, arrivalTime, burstTime, remBurstTime, visit, sc);

        sortProcesses(n, process, arrivalTime, burstTime, remBurstTime);

        shortestJobFirst(n, arrivalTime, burstTime, remBurstTime, visit, completionTime);

        calculateTimes(n, arrivalTime, remBurstTime, completionTime, TAT, waitingTime);

        displayProcesses(n, process, arrivalTime, remBurstTime, completionTime, TAT, waitingTime);

        float avgTAT = 0, avgwt = 0;
        for (int i = 0; i < n; i++) {
            avgTAT += TAT[i];
            avgwt += waitingTime[i];
        }
        avgTAT /= n;
        avgwt /= n;

        System.out.println("\nAverage turn around time is " + avgTAT);
        System.out.println("Average waiting time is " + avgwt);
        sc.close();
    }
}
