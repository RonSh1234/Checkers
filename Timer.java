class Timer {
    long startTime;
    long totalTime; // Total accumulated time in nanoseconds
    double maxTime;// Set maximum time limit (60 seconds)

    public Timer() { 
        this.totalTime = 0;
    }

    public void setMaxTime(int maxTime) {this.maxTime = maxTime * 1_000_000_000.0;}


        public double getMaxTime() {return this.maxTime;}
        public long getTotalTime() {return this.totalTime;}




    public void startTimer() {
        this.startTime = System.nanoTime();
    }

    public void moveTimer() {
        long currentTime = System.nanoTime();
        totalTime += currentTime - startTime;
        double remainingTime = (maxTime - totalTime) / 1_000_000_000.0; // Convert to seconds

        System.out.println("Time remaining: " + remainingTime + " seconds");

        if (totalTime >= maxTime) {
            System.out.println("Time's up! You have lost the game.");
        }
        startTime = currentTime;
    }


    public double getTotalTimeInSeconds() {
        return totalTime / 1_000_000_000.0; 
    }

    Runnable task = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.nanoTime();
            long timeFromBeginning = currentTime - startTime;
            //System.out.println("Time running: " + (timeFromBeginning / 1_000_000_000.0) + " seconds");
        }
    };


    public void resetTimer() {
        totalTime = 0;
    }
}