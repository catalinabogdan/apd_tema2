/* Implement this class. */

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyDispatcher extends Dispatcher {

    public MyDispatcher(SchedulingAlgorithm algorithm, List<Host> hosts) {
        super(algorithm, hosts);
    }
    
    private int lastNode = -1;
    @Override
    public void addTask(Task task) { 
        if(this.algorithm == SchedulingAlgorithm.ROUND_ROBIN) {
            // alternate the nodes to send tasks by incrementing last node's index 
            lastNode = (lastNode + 1) % hosts.size();
            this.hosts.get(lastNode % hosts.size()).addTask(task);
            

        } else if (algorithm == SchedulingAlgorithm.SHORTEST_QUEUE) {
            Host minWhost = hosts.get(0);
            int minIndex = 0;
            for (int i = 0; i < hosts.size(); i ++) {
                // for each host update the node with the shortest queue
                if(hosts.get(i).getQueueSize() < minWhost.getQueueSize()) {
                    minWhost = hosts.get(i);
                    minIndex = i;
                }
            }
            hosts.get(minIndex).addTask(task);

        } else if (algorithm == SchedulingAlgorithm.SIZE_INTERVAL_TASK_ASSIGNMENT) {
            // check type and send task accordingly
            if (task.getType() == TaskType.SHORT) {
                hosts.get(0).addTask(task);
            } else if (task.getType() == TaskType.MEDIUM) {
                hosts.get(1).addTask(task);
            } else if (task.getType() == TaskType.LONG) {
                hosts.get(2).addTask(task);
            }

        } else if (algorithm == SchedulingAlgorithm.LEAST_WORK_LEFT) {
            Host minWhost = hosts.get(0);
            int minIndex = 0;
            for (int i = 0; i < hosts.size(); i ++) {
                // // for each host update the node with minimum work left
                if(hosts.get(i).getWorkLeft() < minWhost.getWorkLeft()) {
                    minWhost = hosts.get(i);
                    minIndex = i;
                }
            }
            hosts.get(minIndex).addTask(task);
        }
    }
}
