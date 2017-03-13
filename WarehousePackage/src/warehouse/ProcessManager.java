package warehouse;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ProcessManager {

  // Map of workers for easy access by name
  protected HashMap<String, Worker> workers = new HashMap<String, Worker>();
  // Queue of jobs to do
  protected ArrayList<Job> jobsToDo = new ArrayList<Job>();
  // Map of jobs in progress
  protected ArrayList<Job> jobsInProgress = new ArrayList<Job>();

  // Master system
  protected WarehouseSystem system;

  /**
   * Default constructor for ProcessManager.
   * 
   * @param system master system
   */
  public ProcessManager(WarehouseSystem system) {
    this.system = system;
  }

  /**
   * Set the status of a worker.
   * 
   * @param name of the worker
   * @param status description
   */
  public void setStatus(String name, String status) {
    getWorker(name).setStatus(status);
    nextJob(name);
  }

  /**
   * Creates and/or checks status of a worker.
   * 
   * @param name name of the worker
   * @return Worker
   */
  protected Worker getWorker(String name) {
    // Get worker from map
    Worker worker = workers.get(name);
    // If no worker exists hire new one
    if (worker == null) {
      worker = hireWorker(name);
    }
    return worker;
  }

  /**
   * Queue job to be performed by worker.
   * 
   * @param job to be done
   */
  public void queueJob(Job job) {
    jobsToDo.add(job);
  }

  /**
   * Give new job to worker or instruct them to continue.
   * 
   * @param name name of the worker
   */
  public void nextJob(String name) {
    Worker worker = getWorker(name);
    if (worker.getCurrentJob() == null) {
      if (jobsToDo.size() == 0) {
        FileHelper.logEvent("Error no job to do");
        return;
      }

      Job job = jobsToDo.remove(0);
      worker.startNextJob(job);
      jobsInProgress.add(job);
      
      if (!(worker instanceof Picker)) {
        worker.nextTask();
      }
    } else {
      worker.nextTask();
    }
  }

  /**
   * Job is complete, hand it off.
   * 
   * @param worker who completed job
   */
  public void jobComplete(Worker worker) {
    Job job = worker.getCurrentJob();
    job.complete();
    jobsInProgress.remove(job);
  }

  /**
   * Discard job.
   * 
   * @param worker who screwed up
   * @param job to discard
   */
  public void discardJob(Worker worker, Job job) {
    FileHelper.logEvent(worker.getRole() + " error. Job " + job.getId() + " discarded");
    jobsInProgress.remove(job);
    system.sendToPicking(job);
  }

  /**
   * Hire designated worker type.
   * 
   * @param name of the worker
   * @return Worker
   */
  public abstract Worker hireWorker(String name);

  /**
   * Add a worker to the worker pool.
   * 
   * @param worker to add
   */
  public void addWorker(Worker worker) {
    workers.put(worker.getName(), worker);
  }
  
  /**
   * Get the jobs to do.
   * @ArrayList
   */
  public ArrayList<Job> getJobsToDo() {
    return jobsToDo;
  }
  
  /**
   * Get the jobs in progress.
   * @ArrayList
   */
  public ArrayList<Job> getJobsInProgress() {
    return jobsInProgress;
  }
}
