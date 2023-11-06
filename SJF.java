
import java.util.*;

public class SJF {
    // Function to sort processes based on arrival time
    public static void sortProcesses(int n, int[] process, int[] arrivalTime, int[] burstTime, int[] remBurstTime) {
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

    // Function to perform Shortest Job First Scheduling
    public static void shortestJobFirst(int n, int[] process, int[] arrivalTime, int[] burstTime) {
        int[] completionTime = new int[n];
        int[] turnAroundTime = new int[n];
        int[] waitingTime = new int[n];
        int[] visited = new int[n];
        int[] remBurstTime = Arrays.copyOf(burstTime, n);
        int total = 0;
        int start = 0;

        while (true) {
            int min = Integer.MAX_VALUE;
            int c = n;

            if (total == n) {
                break;
            }

            for (int i = 0; i < n; i++) {
                if (arrivalTime[i] <= start && visited[i] == 0 && burstTime[i] < min) {
                    min = burstTime[i];
                    c = i;
                }
            }

            if (c == n) {
                start++;
            } else {
                burstTime[c]--;
                start++;
                if (burstTime[c] == 0) {
                    completionTime[c] = start;
                    visited[c] = 1;
                    total++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            turnAroundTime[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = turnAroundTime[i] - remBurstTime[i];
        }

        displayProcessDetails(n, process, arrivalTime, remBurstTime, completionTime, turnAroundTime, waitingTime);
        calculateAverageTimes(n, turnAroundTime, waitingTime);
    }

    // Function to display process details
    public static void displayProcessDetails(int n, int[] process, int[] arrivalTime, int[] remBurstTime,
                                            int[] completionTime, int[] turnAroundTime, int[] waitingTime) {
        System.out.println("*** Shortest Job First Scheduling (Preemptive) ***");
        System.out.println("Processor\tArrival time\tBrust time\tCompletion Time\tTurn around time\tWaiting time");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + process[i] + "\t\t" + arrivalTime[i] + "ms\t\t" + remBurstTime[i] + "ms\t\t"
                    + completionTime[i] + "ms\t\t\t" + turnAroundTime[i] + "ms\t\t\t" + waitingTime[i] + "ms");
        }
    }

    // Function to calculate average turnaround time and waiting time
    public static void calculateAverageTimes(int n, int[] turnAroundTime, int[] waitingTime) {
        float avgTAT = 0, avgWT = 0;
        for (int i = 0; i < n; i++) {
            avgTAT += turnAroundTime[i];
            avgWT += waitingTime[i];
        }
        avgTAT /= n;
        avgWT /= n;
        System.out.println("\nAverage turn around time is " + avgTAT);
        System.out.println("Average waiting time is " + avgWT);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Shortest Job First Scheduling (Preemptive) ***");
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        int[] process = new int[n];
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.println(" ");
            process[i] = (i + 1);
            System.out.print("Enter Arrival Time for processor " + (i + 1) + ": ");
            arrivalTime[i] = sc.nextInt();
            System.out.print("Enter Burst Time for processor " + (i + 1) + ": ");
            burstTime[i] = sc.nextInt();
        }

        sortProcesses(n, process, arrivalTime, burstTime, burstTime);
        shortestJobFirst(n, process, arrivalTime, burstTime);
        sc.close();
    }
}

