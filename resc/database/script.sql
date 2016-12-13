update rzip = trim (rzip) from zip_state_city;

select t.rzip, t.rstate, t.rcity, concat ( coalesce(zsc.rcity,''), coalesce(zsc.rstate,''), coalesce(zsc.rzip,'')) as address
from tar_table t
join zip_state_city zsc on t.rzip = zsc.rzip;

select concat_ws(' ', coalesce(rcity,''), coalesce(rstate,''), coalesce(rzip,'')) as address
from tar_table;
