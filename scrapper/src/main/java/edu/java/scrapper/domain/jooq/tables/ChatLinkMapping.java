/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.domain.jooq.tables;

import edu.java.scrapper.domain.jooq.DefaultSchema;
import edu.java.scrapper.domain.jooq.Keys;
import edu.java.scrapper.domain.jooq.tables.records.ChatLinkMappingRecord;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

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
public class ChatLinkMapping extends TableImpl<ChatLinkMappingRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>CHAT_LINK_MAPPING</code>
     */
    public static final ChatLinkMapping CHAT_LINK_MAPPING = new ChatLinkMapping();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<ChatLinkMappingRecord> getRecordType() {
        return ChatLinkMappingRecord.class;
    }

    /**
     * The column <code>CHAT_LINK_MAPPING.CHAT_ID</code>.
     */
    public final TableField<ChatLinkMappingRecord, Long> CHAT_ID =
        createField(DSL.name("CHAT_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>CHAT_LINK_MAPPING.LINK_ID</code>.
     */
    public final TableField<ChatLinkMappingRecord, Long> LINK_ID =
        createField(DSL.name("LINK_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    private ChatLinkMapping(Name alias, Table<ChatLinkMappingRecord> aliased) {
        this(alias, aliased, null);
    }

    private ChatLinkMapping(Name alias, Table<ChatLinkMappingRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>CHAT_LINK_MAPPING</code> table reference
     */
    public ChatLinkMapping(String alias) {
        this(DSL.name(alias), CHAT_LINK_MAPPING);
    }

    /**
     * Create an aliased <code>CHAT_LINK_MAPPING</code> table reference
     */
    public ChatLinkMapping(Name alias) {
        this(alias, CHAT_LINK_MAPPING);
    }

    /**
     * Create a <code>CHAT_LINK_MAPPING</code> table reference
     */
    public ChatLinkMapping() {
        this(DSL.name("CHAT_LINK_MAPPING"), null);
    }

    public <O extends Record> ChatLinkMapping(Table<O> child, ForeignKey<O, ChatLinkMappingRecord> key) {
        super(child, key, CHAT_LINK_MAPPING);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public UniqueKey<ChatLinkMappingRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_8;
    }

    @Override
    @NotNull
    public List<ForeignKey<ChatLinkMappingRecord, ?>> getReferences() {
        return Arrays.asList(Keys.CONSTRAINT_83, Keys.CONSTRAINT_833);
    }

    private transient Chat _chat;
    private transient Link _link;

    /**
     * Get the implicit join path to the <code>PUBLIC.CHAT</code> table.
     */
    public Chat chat() {
        if (_chat == null) {
            _chat = new Chat(this, Keys.CONSTRAINT_83);
        }

        return _chat;
    }

    /**
     * Get the implicit join path to the <code>PUBLIC.LINK</code> table.
     */
    public Link link() {
        if (_link == null) {
            _link = new Link(this, Keys.CONSTRAINT_833);
        }

        return _link;
    }

    @Override
    @NotNull
    public ChatLinkMapping as(String alias) {
        return new ChatLinkMapping(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public ChatLinkMapping as(Name alias) {
        return new ChatLinkMapping(alias, this);
    }

    @Override
    @NotNull
    public ChatLinkMapping as(Table<?> alias) {
        return new ChatLinkMapping(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public ChatLinkMapping rename(String name) {
        return new ChatLinkMapping(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public ChatLinkMapping rename(Name name) {
        return new ChatLinkMapping(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public ChatLinkMapping rename(Table<?> name) {
        return new ChatLinkMapping(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function2<? super Long, ? super Long, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super Long, ? super Long, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
