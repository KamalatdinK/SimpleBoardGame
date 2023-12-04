package org.example.result;

import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResult {

    @NonNull private String player1Name;
    @NonNull private String player2Name;
    @NonNull private String winnerName;
    private int player1Moves;
    private int player2Moves;
    @NonNull private LocalDateTime startDateTime;

}
