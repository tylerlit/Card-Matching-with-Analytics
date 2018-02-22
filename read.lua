
--part two of semester project
--read output file out.txt from part 1 and generate analytics


string_library = strlib_open
io_library = iolib_open

function readfile()

    player1match = 0
    player2match = 0
    
    player1win = 0
    player2win = 0

    game = 0

    totalguess = 0
    gameguess = 0
    leastguess = 0
    mostguess = 0

    streak = 0
    
    

    io.input("out.txt")

    while true do
        
        line = io.read() 
        if line == nil then break end
        if line == "" then gameguess = gameguess + 1 end

        --check if the game ended
        if string.sub(line, 1, 2) == "it" then
            game = game + 1

            if leastguess == 0 then
                leastguess = gameguess
            end

            if gameguess < leastguess then
                leastguess = gameguess
            end

            if gameguess > mostguess then
                mostguess = gameguess
            end

            totalguess = totalguess + gameguess
            gameguess = 0
        end

        if string.sub(line, 1, 6) == "Winner" then
            game = game + 1

            if leastguess == 0 then
                leastguess = gameguess
            end

            if gameguess < leastguess then
                leastguess = gameguess
            end

            if gameguess > mostguess then
                mostguess = gameguess
            end

            totalguess = totalguess + gameguess
            gameguess = 0

            if string.sub(line, 28, 28) == "2" then
                player2win = player2win + 1
            end
            if string.sub(line, 28, 28) == "1" then
                player1win = player1win + 1
            end
        end

        endgame = false
        
        --match found
        if string.sub(line, 1, 8) == "Congrats" then

            gameguess = gameguess + 1
            
            --score player depending on number of guesses so far
            if gameguess % 2 == 0 then
                player2match = player2match + 1
            end
            if gameguess % 2 == 1 then
                player1match = player1match + 1
            end
            
            newline = false
            currentstreak = 0
            while line ~= "" do
                newline = true 
                line = io.read()
                if line == nil then break end

                if string.sub(line, 1, 8) == "Congrats" then
                    gameguess = gameguess + 1
                    --score player depending on number of guesses so far
                    --keep track of streak
                    
                    currentstreak = currentstreak + 1
                    
                    if gameguess % 2 == 0 then
                        player2match = player2match + 1
                    end
                    if gameguess % 2 == 1 then
                        player1match = player2match + 1
                    end

                    --check if the game ended
                    if string.sub(line, 1, 2) == "it" then
                        game = game + 1
                        
                        if leastguess == 0 then
                            leastguess = gameguess
                        end

                        if gameguess < leastguess then
                            leastguess = gameguess
                        end
                        
                        if gameguess > mostguess then
                            mostguess = gameguess
                        end

                        totalguess = totalguess + gameguess
                        gameguess = 0
                        endgame = true 

                        break
                    end

                    if string.sub(line, 1, 6) == "Winner" then
                        game = game + 1

                        if leastguess == 0 then
                            leastguess = gameguess
                        end

                        if gameguess < leastguess then
                            leastguess = gameguess
                        end

                        if gameguess > mostguess then
                            mostguess = gameguess
                        end 

                        totalguess = totalguess + gameguess 
                        gameguess = 0
                        endgame = true

                        if string.sub(line, 28, 28) == "2" then
                            player2win = player2win + 1
                        end
                        if string.sub(line, 28, 28) == "1" then
                            player1win = player1win + 1
                        end
                        
                        break
                    end
                end
            end
            if line == nil then break end 
            --line break used to break streak
            if newline == true then
                gameguess = gameguess + 1
            end

            --update streak if there
            if currentstreak > streak then
                streak = currentstreak
            end

            --update guesses
            if endgame == false then 
                gameguess = gameguess + currentstreak
            end 

            endgame = false


        end
    end
    --there are 2 line breaks after every game
    totalgeuss = totalguess - game * 2

    print("There were " .. game .. " games")
    print("Average number of matches for player1: " .. player1match / game)
    print("Average number of matches for player2: " .. player2match / game)
    print("Most guesses in one game: " .. mostguess)
    print("Least guesses in one game: " .. leastguess)
    print("Average number of guesses per game: " .. totalguess / game)
    print("Longest streak of matches any player went on: " .. streak)
    print(player1win .. " " .. player2win)

    if player1win > player2win then
        print("Player that won the most: Player 1")
    elseif player2win > player1win then
        print("Player that won the most: Player 2")
    else
        print("Both players won equal amount of games!")
    end
end

readfile()
