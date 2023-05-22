create function public.calculate_profit_airline(query bigint) returns numeric
    language plpgsql
as
$$
DECLARE
itemPrice numeric(10, 2);
begin
SELECT sum(price)
INTO itemPrice
FROM tickets
where id_airline = query;
RETURN itemPrice;
end;
$$;

alter function public.calculate_profit_airline(bigint) owner to development;