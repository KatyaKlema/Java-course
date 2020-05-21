public class NewContext implements Context {
        @Override
        public int getCompleteTaskCount() {
            return finishedTasks.get();
        }

        @Override
        public int getFailedTaskCount() {
            return faildTasks.get();
        }

        @Override
        public int getInterruptedTaskCount() {
            return interruptedTasks.get();
        }

        @Override
        public void interrupt() {
            isInterrupted = true;
        }

        @Override
        public boolean isFinished() {
            return isFinished;
        }
    }
