package edu.java.bot.services;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DatabaseUrlServiceTest {
    @Mock
    DatabaseUrlService urlService;

    @Test
    void testAddUrl_Success() {
        long chatId = 999;
        String url = "https://github.com/AnastasiaPleshkova";
        doNothing().when(urlService).add(chatId, url);

        urlService.add(chatId, url);

        assertDoesNotThrow(() -> urlService.add(chatId, url));
    }

    @Test
    void testAddUrl_ThrowsException_WhenAlreadyTracked() {
        long chatId = 999;
        String url = "https://github.com/AnastasiaPleshkova";
        doThrow(new IllegalArgumentException(DatabaseUrlService.ALREADY_TRACKED_URL_MSG))
            .when(urlService).add(chatId, url);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> urlService.add(chatId, url)
        );
        assertEquals(DatabaseUrlService.ALREADY_TRACKED_URL_MSG, exception.getMessage());
    }

    @Test
    void testRemoveUrl_Success() {
        long chatId = 999;
        String url = "https://github.com/somebody";
        doNothing().when(urlService).remove(chatId, url);

        urlService.remove(chatId, url);

        assertDoesNotThrow(() -> urlService.remove(chatId, url));
    }

    @Test
    void testRemoveUrl_ThrowsException_WhenNotFound() {
        long chatId = 999;
        String url = "https://github.com/somebody";
        doThrow(new IllegalArgumentException(DatabaseUrlService.NOT_FOUND_URL_MSG))
            .when(urlService).remove(chatId, url);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> urlService.remove(chatId, url)
        );
        assertEquals(DatabaseUrlService.NOT_FOUND_URL_MSG, exception.getMessage());
    }

    @Test
    void testGetLinksByUser_Success() {
        long chatId = 999;
        List<String> expectedLinks = List.of("https://github.com/AnastasiaPleshkova", "https://github.com/somebody");
        when(urlService.getLinksByUser(chatId)).thenReturn(expectedLinks);

        List<String> actualLinks = urlService.getLinksByUser(chatId);

        assertEquals(expectedLinks, actualLinks);
    }

    @Test
    void testGetLinksByUser_ThrowsException_WhenUserNotFound() {
        long chatId = 999;
        doThrow(new IllegalArgumentException(DatabaseUrlService.FIRST_SIGH_IN_MSG))
            .when(urlService).getLinksByUser(chatId);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> urlService.getLinksByUser(chatId)
        );
        assertEquals(DatabaseUrlService.FIRST_SIGH_IN_MSG, exception.getMessage());
    }

    @Test
    void testAddUser_Success() {
        long chatId = 999;
        doNothing().when(urlService).addUser(chatId);

        assertDoesNotThrow(() -> urlService.addUser(chatId));
    }

}
