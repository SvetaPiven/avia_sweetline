create procedure public.sale(IN idticket bigint, IN discount real)
    language plpgsql
as
$$
begin
    update tickets
    set price = tickets.price - tickets.price * discount
    where id_ticket = idTicket
      and id_ticket_class = 2;

end;
$$;

alter procedure public.sale(bigint, real) owner to development;