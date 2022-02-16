package com.planner.Events;

public class Event {
    private String title;
    private String description;
    private long startTime;
    private long endTime;

    private Event(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
    }

    public static final class Builder {
        private String title;
        private String description;
        private long startTime;
        private long endTime;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setStartTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setEndTime(long endTime) {
            this.endTime = endTime;
            return this;
        }

        public Event build() {
            if (endTime == 0) endTime = startTime;
            return new Event(this);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
