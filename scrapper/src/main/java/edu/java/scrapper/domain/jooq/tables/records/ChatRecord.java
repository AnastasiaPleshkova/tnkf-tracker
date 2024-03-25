/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.domain.jooq.tables.records;

import edu.java.scrapper.domain.jooq.tables.Chat;
import jakarta.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
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
public class ChatRecord extends UpdatableRecordImpl<ChatRecord> implements Record4<Long, Long, OffsetDateTime, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>CHAT.ID</code>.
     */
    public void setId(@Nullable Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>CHAT.ID</code>.
     */
    @Nullable
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>CHAT.TG_CHAT_ID</code>.
     */
    public void setTgChatId(@NotNull Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>CHAT.TG_CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getTgChatId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>CHAT.CREATED_AT</code>.
     */
    public void setCreatedAt(@NotNull OffsetDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>CHAT.CREATED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getCreatedAt() {
        return (OffsetDateTime) get(2);
    }

    /**
     * Setter for <code>CHAT.CREATED_BY</code>.
     */
    public void setCreatedBy(@NotNull String value) {
        set(3, value);
    }

    /**
     * Getter for <code>CHAT.CREATED_BY</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getCreatedBy() {
        return (String) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row4<Long, Long, OffsetDateTime, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row4<Long, Long, OffsetDateTime, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Chat.CHAT.ID;
    }

    @Override
    @NotNull
    public Field<Long> field2() {
        return Chat.CHAT.TG_CHAT_ID;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field3() {
        return Chat.CHAT.CREATED_AT;
    }

    @Override
    @NotNull
    public Field<String> field4() {
        return Chat.CHAT.CREATED_BY;
    }

    @Override
    @Nullable
    public Long component1() {
        return getId();
    }

    @Override
    @NotNull
    public Long component2() {
        return getTgChatId();
    }

    @Override
    @NotNull
    public OffsetDateTime component3() {
        return getCreatedAt();
    }

    @Override
    @NotNull
    public String component4() {
        return getCreatedBy();
    }

    @Override
    @Nullable
    public Long value1() {
        return getId();
    }

    @Override
    @NotNull
    public Long value2() {
        return getTgChatId();
    }

    @Override
    @NotNull
    public OffsetDateTime value3() {
        return getCreatedAt();
    }

    @Override
    @NotNull
    public String value4() {
        return getCreatedBy();
    }

    @Override
    @NotNull
    public ChatRecord value1(@Nullable Long value) {
        setId(value);
        return this;
    }

    @Override
    @NotNull
    public ChatRecord value2(@NotNull Long value) {
        setTgChatId(value);
        return this;
    }

    @Override
    @NotNull
    public ChatRecord value3(@NotNull OffsetDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    @NotNull
    public ChatRecord value4(@NotNull String value) {
        setCreatedBy(value);
        return this;
    }

    @Override
    @NotNull
    public ChatRecord values(
        @Nullable Long value1,
        @NotNull Long value2,
        @NotNull OffsetDateTime value3,
        @NotNull String value4
    ) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ChatRecord
     */
    public ChatRecord() {
        super(Chat.CHAT);
    }

    /**
     * Create a detached, initialised ChatRecord
     */
    @ConstructorProperties({"id", "tgChatId", "createdAt", "createdBy"})
    public ChatRecord(
        @Nullable Long id,
        @NotNull Long tgChatId,
        @NotNull OffsetDateTime createdAt,
        @NotNull String createdBy
    ) {
        super(Chat.CHAT);

        setId(id);
        setTgChatId(tgChatId);
        setCreatedAt(createdAt);
        setCreatedBy(createdBy);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised ChatRecord
     */
    public ChatRecord(edu.java.scrapper.domain.jooq.tables.pojos.Chat value) {
        super(Chat.CHAT);

        if (value != null) {
            setId(value.getId());
            setTgChatId(value.getTgChatId());
            setCreatedAt(value.getCreatedAt());
            setCreatedBy(value.getCreatedBy());
            resetChangedOnNotNull();
        }
    }
}
