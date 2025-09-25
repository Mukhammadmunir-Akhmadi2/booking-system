-- Events
INSERT INTO events (id, name, date_time, venue, total_tickets, available_tickets) VALUES
    ('11111111-1111-1111-1111-111111111111','Rock Night','2025-10-01 20:00:00+00','City Arena',100,100),
    ('22222222-2222-2222-2222-222222222222','Tech Conference','2025-11-15 09:00:00+00','Expo Center',200,200),
    ('33333333-3333-3333-3333-333333333333','Jazz Evening','2025-12-05 19:30:00+00','Blue Note Hall',80,80);

-- Users
INSERT INTO users (id, full_name, phone_number) VALUES
    ('bbbbbbbb-aaaa-bbbb-cccc-bbbbbbbbbbbb','Alice Smith','+998901000001'),
    ('cccccccc-aaaa-bbbb-cccc-cccccccccccc','Bob Johnson','+998901000002'),
    ('dddddddd-aaaa-bbbb-cccc-dddddddddddd','Carol Lee','+998901000003');

-- Orders
INSERT INTO orders (id, user_id, booking_time, total_amount) VALUES
    ('eeeeeeee-aaaa-bbbb-cccc-eeeeeeeeeeee','bbbbbbbb-aaaa-bbbb-cccc-bbbbbbbbbbbb',NOW(),50.5),
    ('ffffffff-aaaa-bbbb-cccc-ffffffffffff','cccccccc-aaaa-bbbb-cccc-cccccccccccc',NOW(),30.5);

-- Tickets (note the new order_id column for booked tickets)
INSERT INTO tickets (id, sector, row_number, seat_number, date_time, status, price, event_id, order_id) VALUES
    ('11111111-aaaa-bbbb-cccc-111111111111','A',1,1,'2025-10-01 20:00:00+00','BOOKED',50.5,'11111111-1111-1111-1111-111111111111','eeeeeeee-aaaa-bbbb-cccc-eeeeeeeeeeee'),
    ('22222222-aaaa-bbbb-cccc-222222222222','A',1,2,'2025-10-01 20:00:00+00','BOOKED',30.5,'11111111-1111-1111-1111-111111111111','ffffffff-aaaa-bbbb-cccc-ffffffffffff'),
    ('33333333-aaaa-bbbb-cccc-333333333333','B',2,5,'2025-11-15 09:00:00+00','AVAILABLE',55.25,'11111111-1111-1111-1111-111111111111',NULL),
    ('44444444-aaaa-bbbb-cccc-444444444444','C',3,7,'2025-12-05 19:30:00+00','AVAILABLE',33.5,'11111111-1111-1111-1111-111111111111',NULL),
    ('55555555-aaaa-bbbb-cccc-555555555555','VIP',1,1,'2025-09-30 19:00:00+00','AVAILABLE',20,'22222222-2222-2222-2222-222222222222',NULL),
    ('66666666-aaaa-bbbb-cccc-666666666666','D',4,10,'2025-10-20 10:00:00+00','AVAILABLE',20.5,'22222222-2222-2222-2222-222222222222',NULL),
    ('77777777-aaaa-bbbb-cccc-777777777777','E',5,15,'2025-11-01 12:00:00+00','AVAILABLE',30,'22222222-2222-2222-2222-222222222222',NULL),
    ('88888888-aaaa-bbbb-cccc-888888888888','F',6,20,'2025-10-10 09:00:00+00','AVAILABLE',100.9,'33333333-3333-3333-3333-333333333333',NULL),
    ('99999999-aaaa-bbbb-cccc-999999999999','G',7,25,'2025-11-05 14:00:00+00','AVAILABLE',28.5,'33333333-3333-3333-3333-333333333333',NULL),
    ('aaaaaaaa-aaaa-bbbb-cccc-aaaaaaaaaaaa','H',8,30,'2025-12-12 18:00:00+00','AVAILABLE',30.5,'33333333-3333-3333-3333-333333333333',NULL);
