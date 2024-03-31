# tema2

For the realization of this project, it was necessary to update the classes MyHost and MyDispatcher, 
where I specified the abstract methods of the parent classes.

The task distribution algorithms are planned in the MyDispatcher class, where four types of algorithms are differentiated:

- ROUND_ROBIN: sends tasks in a round-robin manner from the first to the last node.
- SHORTEST_QUEUE: selects the node with the fewest tasks in the queue and in execution using the getQueueSize() method.
- SIZE_INTERVAL_TASK_ASSIGNMENT: distributes tasks to specific nodes based on their type.
- LEAST WORK LEFT: sends tasks to the node that finishes execution soonest using the getWorkLeft() method.

To keep track of tasks and perform automatic synchronization, a priority blocking queue is used, which is constructed based on a comparator:
it places tasks in the queue in descending order of priority and ascending order of start time in case of equal priorities.

The addTask method ensures adding to the queue and setting the remaining time to traverse for each task as its total duration. In the run method,
an infinite loop is created based on the constant 'runnable', a boolean variable set to true, which is marked as false on shutdown.

Tasks are extracted from the queue and executed based on their characteristics: preemptibility. If a task is not preemptible, its execution cannot be
interrupted, so the thread waits periodically until all the necessary time for completion has been consumed.

On the other hand, if this field is set to true, after each sleep period, it is checked if a task with higher priority than the current one has been
added to the queue, in which case the execution is paused, it is added to the queue, and the execution of the higher-priority task begins.

    
