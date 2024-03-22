/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.jooq.tables.Chat;
import edu.java.scrapper.domain.jooq.tables.ChatLinkMapping;
import edu.java.scrapper.domain.jooq.tables.Link;
import edu.java.scrapper.domain.jooq.tables.records.ChatLinkMappingRecord;
import edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import edu.java.scrapper.domain.jooq.tables.records.LinkRecord;
import javax.annotation.processing.Generated;
import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ChatRecord> CONSTRAINT_1 =
        Internal.createUniqueKey(Chat.CHAT, DSL.name("CONSTRAINT_1"), new TableField[] {Chat.CHAT.ID}, true);
    public static final UniqueKey<ChatLinkMappingRecord> CONSTRAINT_8 =
        Internal.createUniqueKey(ChatLinkMapping.CHAT_LINK_MAPPING,
            DSL.name("CONSTRAINT_8"),
            new TableField[] {ChatLinkMapping.CHAT_LINK_MAPPING.CHAT_ID, ChatLinkMapping.CHAT_LINK_MAPPING.LINK_ID},
            true
        );
    public static final UniqueKey<LinkRecord> CONSTRAINT_2 =
        Internal.createUniqueKey(Link.LINK, DSL.name("CONSTRAINT_2"), new TableField[] {Link.LINK.ID}, true);
    public static final UniqueKey<LinkRecord> CONSTRAINT_23 =
        Internal.createUniqueKey(Link.LINK, DSL.name("CONSTRAINT_23"), new TableField[] {Link.LINK.URL}, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ChatLinkMappingRecord, ChatRecord> CONSTRAINT_83 = Internal.createForeignKey(
        ChatLinkMapping.CHAT_LINK_MAPPING,
        DSL.name("CONSTRAINT_83"),
        new TableField[] {ChatLinkMapping.CHAT_LINK_MAPPING.CHAT_ID},
        Keys.CONSTRAINT_1,
        new TableField[] {Chat.CHAT.ID},
        true
    );
    public static final ForeignKey<ChatLinkMappingRecord, LinkRecord> CONSTRAINT_833 = Internal.createForeignKey(
        ChatLinkMapping.CHAT_LINK_MAPPING,
        DSL.name("CONSTRAINT_833"),
        new TableField[] {ChatLinkMapping.CHAT_LINK_MAPPING.LINK_ID},
        Keys.CONSTRAINT_2,
        new TableField[] {Link.LINK.ID},
        true
    );
}
