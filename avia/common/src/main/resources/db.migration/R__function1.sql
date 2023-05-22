create function public.find_most_expensive_ticket(id_p bigint) returns numeric
    language plpgsql
as
$$
DECLARE
itemPrice numeric(10, 2);
begin
SELECT MAX(price)
INTO itemPrice
FROM tickets
WHERE id_pass = id_p;
RETURN itemPrice;
end;
$$;

alter function public.find_most_expensive_ticket(bigint) owner to development;