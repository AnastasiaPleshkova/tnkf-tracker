/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.domain.jooq.tables.pojos;

import jakarta.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String url;
    private OffsetDateTime lastCheckTime;
    private OffsetDateTime updatedAt;
    private Long answersCount;
    private Long commitsCount;
    private OffsetDateTime createdAt;
    private String createdBy;

    public Link() {
    }

    public Link(Link value) {
        this.id = value.id;
        this.url = value.url;
        this.lastCheckTime = value.lastCheckTime;
        this.updatedAt = value.updatedAt;
        this.answersCount = value.answersCount;
        this.commitsCount = value.commitsCount;
        this.createdAt = value.createdAt;
        this.createdBy = value.createdBy;
    }

    @ConstructorProperties({"id", "url", "lastCheckTime", "updatedAt", "answersCount", "commitsCount", "createdAt",
        "createdBy"})
    public Link(
        @Nullable Long id,
        @NotNull String url,
        @NotNull OffsetDateTime lastCheckTime,
        @NotNull OffsetDateTime updatedAt,
        @Nullable Long answersCount,
        @Nullable Long commitsCount,
        @NotNull OffsetDateTime createdAt,
        @NotNull String createdBy
    ) {
        this.id = id;
        this.url = url;
        this.lastCheckTime = lastCheckTime;
        this.updatedAt = updatedAt;
        this.answersCount = answersCount;
        this.commitsCount = commitsCount;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    /**
     * Getter for <code>LINK.ID</code>.
     */
    @Nullable
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>LINK.ID</code>.
     */
    public void setId(@Nullable Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>LINK.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for <code>LINK.URL</code>.
     */
    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    /**
     * Getter for <code>LINK.LAST_CHECK_TIME</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getLastCheckTime() {
        return this.lastCheckTime;
    }

    /**
     * Setter for <code>LINK.LAST_CHECK_TIME</code>.
     */
    public void setLastCheckTime(@NotNull OffsetDateTime lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }

    /**
     * Getter for <code>LINK.UPDATED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * Setter for <code>LINK.UPDATED_AT</code>.
     */
    public void setUpdatedAt(@NotNull OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Getter for <code>LINK.ANSWERS_COUNT</code>.
     */
    @Nullable
    public Long getAnswersCount() {
        return this.answersCount;
    }

    /**
     * Setter for <code>LINK.ANSWERS_COUNT</code>.
     */
    public void setAnswersCount(@Nullable Long answersCount) {
        this.answersCount = answersCount;
    }

    /**
     * Getter for <code>LINK.COMMITS_COUNT</code>.
     */
    @Nullable
    public Long getCommitsCount() {
        return this.commitsCount;
    }

    /**
     * Setter for <code>LINK.COMMITS_COUNT</code>.
     */
    public void setCommitsCount(@Nullable Long commitsCount) {
        this.commitsCount = commitsCount;
    }

    /**
     * Getter for <code>LINK.CREATED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Setter for <code>LINK.CREATED_AT</code>.
     */
    public void setCreatedAt(@NotNull OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Getter for <code>LINK.CREATED_BY</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * Setter for <code>LINK.CREATED_BY</code>.
     */
    public void setCreatedBy(@NotNull String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Link other = (Link) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!this.url.equals(other.url)) {
            return false;
        }
        if (this.lastCheckTime == null) {
            if (other.lastCheckTime != null) {
                return false;
            }
        } else if (!this.lastCheckTime.equals(other.lastCheckTime)) {
            return false;
        }
        if (this.updatedAt == null) {
            if (other.updatedAt != null) {
                return false;
            }
        } else if (!this.updatedAt.equals(other.updatedAt)) {
            return false;
        }
        if (this.answersCount == null) {
            if (other.answersCount != null) {
                return false;
            }
        } else if (!this.answersCount.equals(other.answersCount)) {
            return false;
        }
        if (this.commitsCount == null) {
            if (other.commitsCount != null) {
                return false;
            }
        } else if (!this.commitsCount.equals(other.commitsCount)) {
            return false;
        }
        if (this.createdAt == null) {
            if (other.createdAt != null) {
                return false;
            }
        } else if (!this.createdAt.equals(other.createdAt)) {
            return false;
        }
        if (this.createdBy == null) {
            if (other.createdBy != null) {
                return false;
            }
        } else if (!this.createdBy.equals(other.createdBy)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
        result = prime * result + ((this.lastCheckTime == null) ? 0 : this.lastCheckTime.hashCode());
        result = prime * result + ((this.updatedAt == null) ? 0 : this.updatedAt.hashCode());
        result = prime * result + ((this.answersCount == null) ? 0 : this.answersCount.hashCode());
        result = prime * result + ((this.commitsCount == null) ? 0 : this.commitsCount.hashCode());
        result = prime * result + ((this.createdAt == null) ? 0 : this.createdAt.hashCode());
        result = prime * result + ((this.createdBy == null) ? 0 : this.createdBy.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Link (");

        sb.append(id);
        sb.append(", ").append(url);
        sb.append(", ").append(lastCheckTime);
        sb.append(", ").append(updatedAt);
        sb.append(", ").append(answersCount);
        sb.append(", ").append(commitsCount);
        sb.append(", ").append(createdAt);
        sb.append(", ").append(createdBy);

        sb.append(")");
        return sb.toString();
    }
}