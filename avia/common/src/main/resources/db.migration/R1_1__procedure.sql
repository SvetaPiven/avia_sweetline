CREATE OR REPLACE PROCEDURE IF NOT EXISTS sale(idticket bigint, discount real)
LANGUAGE plpgsql
AS $$
BEGIN

UPDATE tickets
SET price = tickets.price - tickets.price * discount
WHERE id_ticket = idticket AND id_ticket_class = 2;

COMMIT;
END;
$$;