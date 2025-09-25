CREATE TABLE IF NOT EXISTS events (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    date_time TIMESTAMPTZ NOT NULL,
    venue VARCHAR(255),
    total_tickets INTEGER NOT NULL CHECK (total_tickets >= 0),
    available_tickets INTEGER NOT NULL CHECK (available_tickets >= 0)
    );

CREATE TABLE IF NOT EXISTS tickets (
    id UUID PRIMARY KEY,
    sector VARCHAR(50) NOT NULL,
    row_number INTEGER NOT NULL CHECK (row_number > 0),
    seat_number INTEGER NOT NULL CHECK (seat_number > 0),
    date_time TIMESTAMPTZ NOT NULL,
    event_id UUID NOT NULL REFERENCES events(id)
    );

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id),
    ticket_id UUID UNIQUE NOT NULL REFERENCES tickets(id),
    booking_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
    );
