package es.flakiness.bffl;

import junit.framework.TestCase;

import java.util.Date;

public class BuildPresoTest extends TestCase {

    static public class TestingBuildPreso extends BuildPreso {

        public TestingBuildPreso(Build model) {
            super(model, null);
        }

        @Override
        public Date now() {
            return BuildPreso.parseStubDate("2014/10/31-11:30:00");
        }
    };

    public void testHello() {
        BuildPreso target = new TestingBuildPreso(Build.create(
                Long.valueOf(BuildStatus.PASSED.sequence()),
                "make",
                BuildPreso.parseStubDate("2014/10/31-09:00:00"),
                BuildPreso.parseStubDate("2014/10/31-11:00:00")));
        assertEquals("30 minutes ago, took 2 hours", target.getTimestampText());
        assertEquals("Passed!", target.getStatusText());
    }
}
