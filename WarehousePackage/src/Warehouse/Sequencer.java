package warehouse;

public class Sequencer extends Worker {

  /**
   * Default constructor for sequencer.
   * @param name name of the sequencer
   * @param manager manager of this sequencer
   */
  public Sequencer(String name, ProcessManager manager) {
    super(name, "Sequencer", manager);
  }

  /**
   * Perform the next task in the job.
   */
  public void nextTask() {
    currentJob.setPallets(new Pallet[2]);
    jobComplete();
  }
}
