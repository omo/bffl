package es.flakiness.bffl;

import java.util.Date;

public class Build {
    public Long _id;
    public Long status;
    public String report;
    public Date startedAt;
    public Date finishedAt;

    public static Build create(Long status, String report, Date startedAt, Date finishedAt) {
        Build self = new Build();
        self.status = status;
        self.report = report;
        self.startedAt = startedAt;
        self.finishedAt = finishedAt;
        return self;
    }
}
