
import java.util.*;

class Process {
    int processID;
    int arrival, burst, waiting, turnAround, remainingTime;
    int finish, completionTime;
}

public class RRScheduling {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n*.*.program for RR cpu Scheduling.*.*\n");
        int n = getNumberOfProcesses(sc);
        Process[] processes = inputProcessDetails(sc, n);

        int sumBurst = calculateSumBurstTime(processes, n);
        int quantum = getQuantumTime(sc);

        performRRScheduling(processes, n, sumBurst, quantum);
        displaySchedulingTable(processes, n);

        sc.close();
    }

    private static int getNumberOfProcesses(Scanner sc) {
        System.out.print("Enter Number of Processes: ");
        return sc.nextInt();
    }

    private static Process[] inputProcessDetails(Scanner sc, int n) {
        Process[] processes = new Process[n];
        for (int i = 0; i < n; i++) {
            processes[i] = new Process();
            processes[i].processID = i + 1;
            System.out.print("Enter P" + processes[i].processID + " AT: ");
            processes[i].arrival = sc.nextInt();
            System.out.print("Enter P" + processes[i].processID + " BT: ");
            processes[i].burst = sc.nextInt();
            processes[i].remainingTime = processes[i].burst;
            processes[i].finish = 0;
            System.out.println();
        }
        return processes;
    }

    private static int calculateSumBurstTime(Process[] processes, int n) {
        int sumBurst = 0;
        for (int i = 0; i < n; i++) {
            sumBurst += processes[i].burst;
        }
        return sumBurst;
    }

    private static int getQuantumTime(Scanner sc) {
        System.out.print("Enter TQ: ");
        return sc.nextInt();
    }

    private static void performRRScheduling(Process[] processes, int n, int sumBurst, int quantum) {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (processes[i].arrival > processes[j].arrival) {
                    Process temp = processes[i];
                    processes[i] = processes[j];
                    processes[j] = temp;
                }
            }
        }
        queue.add(0);
        for (int time = processes[0].arrival; time < sumBurst;) {
            Integer I = queue.remove();
            int i = I.intValue();
            if (processes[i].remainingTime <= quantum) {
                time += processes[i].remainingTime;
                processes[i].remainingTime = 0;
                processes[i].finish = 1;
                processes[i].completionTime = time;
                processes[i].waiting = time - processes[i].arrival - processes[i].burst;
                processes[i].turnAround = time - processes[i].arrival;
                for (int j = 0; j < n; j++) {
                    Integer J = Integer.valueOf(j);
                    if ((processes[j].arrival <= time) && (processes[j].finish != 1) && (!queue.contains(J)))
                        queue.add(j);
                }
            } else {
                time += quantum;
                processes[i].remainingTime -= quantum;
                for (int j = 0; j < n; j++) {
                    Integer J = Integer.valueOf(j);
                    if (processes[j].arrival <= time && processes[j].finish != 1 && i != j && (!queue.contains(J)))
                        queue.add(j);
                }
                queue.add(i);
            }
        }
    }

    private static void displaySchedulingTable(Process[] processes, int n) {
        System.out.println("\nProcessor\tArrival time\tBrust time\tCompletion Time\t\tTurn around time\tWaiting time");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        double avgWAT = 0, avgTAT = 0;
        for (int i = 0; i < n; i++) {
            System.out.println("P" + processes[i].processID + "\t\t" + processes[i].arrival + "ms\t\t" + processes[i].burst + "ms\t\t"
                    + processes[i].completionTime + "ms\t\t\t" + processes[i].turnAround + "ms\t\t\t" + processes[i].waiting + "ms");
            avgWAT += processes[i].waiting;
            avgTAT += processes[i].turnAround;
        }
        System.out.println("\nAverage turn around time of processor: " + (avgTAT / n)
                + "ms\nAverage waiting time of processor: " + (avgWAT / n) + "ms");
    }
}
