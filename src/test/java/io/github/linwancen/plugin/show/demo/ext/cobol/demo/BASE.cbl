
123456  INITIALIZE HELLO-WORLD
        IF (KEY NOT = '1' AND '2') AND
            KEY NOT = ('1' OR '2') AND
                KEY NOT = '1' THEN
            MOVE WS-HELLO-WORLD TO HELLO-WORLD
        END IF
        TABLE(STRUCT)
        HELLO-WORLD(1:1)
        PERFORM B001-A
      * PERFORM STRUCT