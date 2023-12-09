package model;

public class Status {
    private int omitted;
    private int confirmed;
    private int candidates;

    public Status(int omitted, int confirmed, int candidates) {
        this.omitted = omitted;
        this.confirmed = confirmed;
        this.candidates = candidates;
    }

    public int getOmitted() {
        return omitted;
    }

    public void setOmitted(int omitted) {
        this.omitted = omitted;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getCandidates() {
        return candidates;
    }

    public void setCandidates(int candidates) {
        this.candidates = candidates;
    }
}
