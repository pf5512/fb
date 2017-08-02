-- p1 t1 A 
-- P1 t1 B (OK)
-- p1 t1 A (X)
ALTER TABLE team_player
ADD CONSTRAINT uc_pid_tid_status UNIQUE (player_id, team_id, status)
