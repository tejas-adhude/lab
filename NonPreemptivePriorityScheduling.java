
import java.util.Scanner;

public class NonPreemptivePriorityScheduling {

    public static void main(String[] args) {
        System.out.println("*** Priority Scheduling (Non Preemptive) ***");
        Scanner sc = new Scanner(System.in);

        int n = getNumberOfProcesses(sc);
        int[] process = new int[n];
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] priority = new int[n];
        int[] TAT = new int[n];
        int[] waitingTime = new int[n];

        inputProcessDetails(sc, n, process, arrivalTime, burstTime, priority);
        sortProcessesByArrivalTimeAndPriority(n, process, arrivalTime, burstTime, priority);

        int totalTime = calculateTotalTime(n, arrivalTime, burstTime);
        performScheduling(n, arrivalTime, burstTime, completionTime, priority, TAT, waitingTime, totalTime);

        displayResults(n, process, arrivalTime, burstTime, completionTime, TAT, waitingTime);

        sc.close();
    }

    private static int getNumberOfProcesses(Scanner sc) {
        System.out.print("Enter Number of Processes: ");
        return sc.nextInt();
    }

    private static void inputProcessDetails(Scanner sc, int n, int[] process, int[] arrivalTime, int[] burstTime, int[] priority) {
        for (int i = 0; i < n; i++) {
            process[i] = i + 1;
            System.out.print("\nEnter Arrival Time for Processor " + process[i] + ": ");
            arrivalTime[i] = sc.nextInt();
            System.out.print("Enter Burst Time for Processor " + process[i] + ": ");
            burstTime[i] = sc.nextInt();
            System.out.print("Enter Priority for Processor " + process[i] + ": ");
            priority[i] = sc.nextInt();
        }
    }

    private static void sortProcessesByArrivalTimeAndPriority(int n, int[] process, int[] arrivalTime, int[] burstTime, int[] priority) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                // Sorting based on arrival time
                if (arrivalTime[i] > arrivalTime[j]) {
                    swapProcesses(i, j, process, arrivalTime, burstTime, priority);
                } else if (arrivalTime[i] == arrivalTime[j] && priority[j] < priority[i]) {
                    swapProcesses(i, j, process, arrivalTime, burstTime, priority);
                }
            }
        }
    }

    private static void swapProcesses(int i, int j, int[] process, int[] arrivalTime, int[] burstTime, int[] priority) {
        int temp;
        temp = process[i];
        process[i] = process[j];
        process[j] = temp;
        temp = arrivalTime[j];
        arrivalTime[j] = arrivalTime[i];
        arrivalTime[i] = temp;
        temp = priority[j];
        priority[j] = priority[i];
        priority[i] = temp;
        temp = burstTime[j];
        burstTime[j] = burstTime[i];
        burstTime[i] = temp;
    }

    private static int calculateTotalTime(int n, int[] arrivalTime, int[] burstTime) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (arrivalTime[i] < min) {
                min = arrivalTime[i];
            }
        }

        int totalTime = min;
        for (int i = 0; i < n; i++) {
            totalTime += burstTime[i];
        }
        return totalTime;
    }

    private static void performScheduling(int n, int[] arrivalTime, int[] burstTime, int[] completionTime,
                                          int[] priority, int[] TAT, int[] waitingTime, int totalTime) {
        int[] arrivalTimeCopy = arrivalTime.clone();
        int[] burstTimeCopy = burstTime.clone();

        int tLap = arrivalTime[0];
        while (tLap < totalTime) {
            int minIndex = -1;
            for (int i = 0; i < n; i++) {
                if (arrivalTimeCopy[i] <= tLap) {
                    if (minIndex == -1 || priority[i] < priority[minIndex]) {
                        minIndex = i;
                    }
                }
            }
            tLap += burstTimeCopy[minIndex];
            completionTime[minIndex] = tLap;
            priority[minIndex] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < n; i++) {
            TAT[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = TAT[i] - burstTime[i];
        }
    }

    private static void displayResults(int n, int[] process, int[] arrivalTime, int[] burstTime,
                                       int[] completionTime, int[] TAT, int[] waitingTime) {
        System.out.println("\n*** Priority Scheduling (Non Preemptive) ***");
        System.out.println("Processor\tArrival time\tBurst time\tCompletion Time\tTurn around time\tWaiting time");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + process[i] + "\t\t" + arrivalTime[i] + "ms\t\t" + burstTime[i] + "ms\t\t"
                    + completionTime[i] + "ms\t\t\t" + TAT[i] + "ms\t\t\t" + waitingTime[i] + "ms");
        }

        double avgWT = calculateAverage(waitingTime, n);
        double avgTAT = calculateAverage(TAT, n);
        System.out.println("\nAverage Waiting Time: " + avgWT);
        System.out.println("Average Turn Around Time: " + avgTAT);
    }

    private static double calculateAverage(int[] array, int n) {
        double total = 0;
        for (int i = 0; i < n; i++) {
            total += array[i];
        }
        return total / n;
    }
}

