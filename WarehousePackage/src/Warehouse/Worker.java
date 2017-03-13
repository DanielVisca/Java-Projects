package warehouse;

public abstract class Worker {

  // Unique name of the worker
  private String name;
  // Role of the worker
  private String role;
  // Current job the worker is performing
  protected Job currentJob;
  // The last status reported by the worker
  private String lastStatus;
  // Manager of software
  protected ProcessManager manager;

  /**
   * Default constructor for worker.
   * @param name name of the worker
   */
  public Worker(String name, String role, ProcessManager manager) {
    this.name = name;
    this.role = role;
    this.manager = manager;
  }

  /**
   * Parse the next job.
   * @param job job to parse
   * @return boolean 
   */
  public boolean startNextJob(Job job) {
    if (currentJob == null) {
      currentJob = job;
      currentJob.setWorker(this);
      logTask("has begun job " + job);
      return true;
    } else {
      logTask("already has a job");
      return false;
    }
  }
  
  /**
   * Set lastStatus.
   * @param lastStatus to set
   */
  public void setStatus(String lastStatus) {
    this.lastStatus = lastStatus;
    if (lastStatus == "ready") {
      logTask("ready");
    }
  }
  
  /**
   * Get lastStatus.
   * @return String
   */
  public String getStatus() {
    return lastStatus;
  }
  
  /**
   * Get name.
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * Get the current job.
   * @return Job
   */
  public Job getCurrentJob() {
    return currentJob;
  }

   
  /**
   * Get the role of this worker.
   * @return String
   */
  public String getRole() {
    return role;
  }

  /**
   * Perform the next task in the job.
   */
  public abstract void nextTask();

  /**
   * Log a completed task of this worker.
   * @param task to log
   */
  protected void logTask(String task) {
    FileHelper.logEvent(role + " " + name + " " + task);
  }

  /**
   * The job is complete, report it.
   */
  protected void jobComplete() {
    manager.jobComplete(this);
    currentJob = null;
    setStatus("ready");
  }

  /**
   * Verify the job has been done correctly.
   */
  protected void verifyJob() {
    if (!currentJob.verify()) {
      discardCurrentJob();
    }
  }

  /**
   * Discard the current job.
   */
  protected void discardCurrentJob() {
    manager.discardJob(this, currentJob);
    currentJob.discardContents();
    currentJob = null;
  }

  /**
   * String representation of the worker.
   */
  public String toString() {
    return name;
  }
  
  /**
   * Get manager.
   * @return ProcessManager
   */
  public ProcessManager getManager() {
    return manager;
  }
}
