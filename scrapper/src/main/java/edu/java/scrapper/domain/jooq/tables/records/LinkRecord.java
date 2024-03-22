/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.domain.jooq.tables.records;

import edu.java.scrapper.domain.jooq.tables.Link;
import jakarta.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;

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
public class LinkRecord extends UpdatableRecordImpl<LinkRecord>
    implements Record8<Long, String, OffsetDateTime, OffsetDateTime, Long, Long, OffsetDateTime, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LINK.ID</code>.
     */
    public void setId(@Nullable Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINK.ID</code>.
     */
    @Nullable
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>LINK.URL</code>.
     */
    public void setUrl(@NotNull String value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINK.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>LINK.LAST_CHECK_TIME</code>.
     */
    public void setLastCheckTime(@NotNull OffsetDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>LINK.LAST_CHECK_TIME</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getLastCheckTime() {
        return (OffsetDateTime) get(2);
    }

    /**
     * Setter for <code>LINK.UPDATED_AT</code>.
     */
    public void setUpdatedAt(@NotNull OffsetDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>LINK.UPDATED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getUpdatedAt() {
        return (OffsetDateTime) get(3);
    }

    /**
     * Setter for <code>LINK.ANSWERS_COUNT</code>.
     */
    public void setAnswersCount(@Nullable Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>LINK.ANSWERS_COUNT</code>.
     */
    @Nullable
    public Long getAnswersCount() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>LINK.COMMITS_COUNT</code>.
     */
    public void setCommitsCount(@Nullable Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>LINK.COMMITS_COUNT</code>.
     */
    @Nullable
    public Long getCommitsCount() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>LINK.CREATED_AT</code>.
     */
    public void setCreatedAt(@NotNull OffsetDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>LINK.CREATED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getCreatedAt() {
        return (OffsetDateTime) get(6);
    }

    /**
     * Setter for <code>LINK.CREATED_BY</code>.
     */
    public void setCreatedBy(@NotNull String value) {
        set(7, value);
    }

    /**
     * Getter for <code>LINK.CREATED_BY</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getCreatedBy() {
        return (String) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row8<Long, String, OffsetDateTime, OffsetDateTime, Long, Long, OffsetDateTime, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row8<Long, String, OffsetDateTime, OffsetDateTime, Long, Long, OffsetDateTime, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Link.LINK.ID;
    }

    @Override
    @NotNull
    public Field<String> field2() {
        return Link.LINK.URL;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field3() {
        return Link.LINK.LAST_CHECK_TIME;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field4() {
        return Link.LINK.UPDATED_AT;
    }

    @Override
    @NotNull
    public Field<Long> field5() {
        return Link.LINK.ANSWERS_COUNT;
    }

    @Override
    @NotNull
    public Field<Long> field6() {
        return Link.LINK.COMMITS_COUNT;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field7() {
        return Link.LINK.CREATED_AT;
    }

    @Override
    @NotNull
    public Field<String> field8() {
        return Link.LINK.CREATED_BY;
    }

    @Override
    @Nullable
    public Long component1() {
        return getId();
    }

    @Override
    @NotNull
    public String component2() {
        return getUrl();
    }

    @Override
    @NotNull
    public OffsetDateTime component3() {
        return getLastCheckTime();
    }

    @Override
    @NotNull
    public OffsetDateTime component4() {
        return getUpdatedAt();
    }

    @Override
    @Nullable
    public Long component5() {
        return getAnswersCount();
    }

    @Override
    @Nullable
    public Long component6() {
        return getCommitsCount();
    }

    @Override
    @NotNull
    public OffsetDateTime component7() {
        return getCreatedAt();
    }

    @Override
    @NotNull
    public String component8() {
        return getCreatedBy();
    }

    @Override
    @Nullable
    public Long value1() {
        return getId();
    }

    @Override
    @NotNull
    public String value2() {
        return getUrl();
    }

    @Override
    @NotNull
    public OffsetDateTime value3() {
        return getLastCheckTime();
    }

    @Override
    @NotNull
    public OffsetDateTime value4() {
        return getUpdatedAt();
    }

    @Override
    @Nullable
    public Long value5() {
        return getAnswersCount();
    }

    @Override
    @Nullable
    public Long value6() {
        return getCommitsCount();
    }

    @Override
    @NotNull
    public OffsetDateTime value7() {
        return getCreatedAt();
    }

    @Override
    @NotNull
    public String value8() {
        return getCreatedBy();
    }

    @Override
    @NotNull
    public LinkRecord value1(@Nullable Long value) {
        setId(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value2(@NotNull String value) {
        setUrl(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value3(@NotNull OffsetDateTime value) {
        setLastCheckTime(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value4(@NotNull OffsetDateTime value) {
        setUpdatedAt(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value5(@Nullable Long value) {
        setAnswersCount(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value6(@Nullable Long value) {
        setCommitsCount(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value7(@NotNull OffsetDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value8(@NotNull String value) {
        setCreatedBy(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord values(
        @Nullable Long value1,
        @NotNull String value2,
        @NotNull OffsetDateTime value3,
        @NotNull OffsetDateTime value4,
        @Nullable Long value5,
        @Nullable Long value6,
        @NotNull OffsetDateTime value7,
        @NotNull String value8
    ) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LinkRecord
     */
    public LinkRecord() {
        super(Link.LINK);
    }

    /**
     * Create a detached, initialised LinkRecord
     */
    @ConstructorProperties({"id", "url", "lastCheckTime", "updatedAt", "answersCount", "commitsCount", "createdAt",
        "createdBy"})
    public LinkRecord(
        @Nullable Long id,
        @NotNull String url,
        @NotNull OffsetDateTime lastCheckTime,
        @NotNull OffsetDateTime updatedAt,
        @Nullable Long answersCount,
        @Nullable Long commitsCount,
        @NotNull OffsetDateTime createdAt,
        @NotNull String createdBy
    ) {
        super(Link.LINK);

        setId(id);
        setUrl(url);
        setLastCheckTime(lastCheckTime);
        setUpdatedAt(updatedAt);
        setAnswersCount(answersCount);
        setCommitsCount(commitsCount);
        setCreatedAt(createdAt);
        setCreatedBy(createdBy);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised LinkRecord
     */
    public LinkRecord(edu.java.scrapper.domain.jooq.tables.pojos.Link value) {
        super(Link.LINK);

        if (value != null) {
            setId(value.getId());
            setUrl(value.getUrl());
            setLastCheckTime(value.getLastCheckTime());
            setUpdatedAt(value.getUpdatedAt());
            setAnswersCount(value.getAnswersCount());
            setCommitsCount(value.getCommitsCount());
            setCreatedAt(value.getCreatedAt());
            setCreatedBy(value.getCreatedBy());
            resetChangedOnNotNull();
        }
    }
}
