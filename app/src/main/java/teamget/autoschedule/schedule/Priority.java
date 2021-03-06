package teamget.autoschedule.schedule;

import android.content.res.Resources;

public abstract class Priority {
    static int priorityCount = 0;
    public int rank;

    public Priority(int rank) {
        this.rank = rank;
        priorityCount++;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public abstract double getScoreMultiplier(Timetable t);

    public double getScore(Timetable t) {
        double max = priorityCount + 1 - rank;
        double score = getScoreMultiplier(t) * max;
        return score;
    }

    public abstract String toString(Resources r);
}
