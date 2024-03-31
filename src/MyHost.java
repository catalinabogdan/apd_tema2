/* Implement this class. */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;


import javax.management.Query;

public class MyHost extends Host {
    private Comparator<Task> comparator() {
        return Comparator
                .comparingInt(Task::getPriority).reversed()
                .thenComparingInt(Task::getStart);
    }
    private Queue<Task> my_tasks = new PriorityBlockingQueue<>(20,comparator()); 
    private Task currTask;
    private volatile boolean running;
    private boolean notEmpty;

    
    @Override
    public void run() {
        running = true;
        while (running) {
            if(!my_tasks.isEmpty()) {
                // set value notEmpty true as to know when a task is being executed
                notEmpty = true;
                // extract task with highest priority and smallest start time from queue
                currTask = my_tasks.poll();
                executeTask();
                currTask.finish();
            } else {
                // there is no task currently being executed
                notEmpty = false;
            }       
        }
    }
    private void executeTask() {
        if(!currTask.isPreemptible()) {
            // if the task's execution cannot be interrupted
            try{
                while(currTask.getLeft() > 0) {
                    Thread.sleep(50);
                    // sleep a multiple of 100 miliseconds until the task is finished
                    // and update task's work left after every awekening 
                    currTask.setLeft(currTask.getLeft() - 50);
                }
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            while(currTask.getLeft() > 0) {
                try{
                    Thread.sleep(50);
                    currTask.setLeft(currTask.getLeft() - 50);
                    if(!my_tasks.isEmpty() && currTask != null){
                        // if there's a new task in the queue with higher priority
                        // and the current task is preemptible
                        // switch tasks
                        if(my_tasks.peek().getPriority() > currTask.getPriority()) {
                            Task aux = currTask;
                            currTask = my_tasks.poll();
                            my_tasks.add(aux);
                        }
                    }
                } catch(InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public void addTask(Task task) {
        task.setLeft(task.getDuration());
        my_tasks.add(task);
    }

    @Override
    public synchronized int getQueueSize() {
        int s;
        s = my_tasks.size();
        if(notEmpty) {
            // if a task is currently being executed
            s += 1;
        }
        return s;
    }

    @Override
    public synchronized long getWorkLeft() {
        long work_left = 0;
        ArrayList<Task> arrTasks = new ArrayList<>(my_tasks);
        for (Task t : arrTasks) {
            work_left += t.getLeft();
        }
        if(notEmpty) {
            // if a task is currently being executed
            work_left += currTask.getLeft();
        }
        return work_left;
    }

    @Override
    public void shutdown() {
        running = false; 
    }
}
