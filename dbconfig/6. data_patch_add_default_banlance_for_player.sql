insert into player_deposit_account(player_id, team_id, balances, create_by, create_dt, update_by, update_dt)
select a.player_id, a.team_id, 0.00, a.`create_by`, a.`create_dt`, a.`update_by`, a.`update_dt` from team_player a where status = 'ACTIVE'
