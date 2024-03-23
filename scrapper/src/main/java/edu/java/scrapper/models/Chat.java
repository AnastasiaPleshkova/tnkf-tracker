package edu.java.scrapper.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "tg_chat_id")
    private Long tgChatId;
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    @Column(name = "created_by")
    private String createdBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "chat_link_mapping",
               joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "id"))
    Set<Link> links;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Chat chat = (Chat) o;

        return Objects.equals(tgChatId, chat.tgChatId);
    }

    @Override
    public int hashCode() {
        return tgChatId != null ? tgChatId.hashCode() : 0;
    }
}
