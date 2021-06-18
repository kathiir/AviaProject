import psycopg2
import configuration


class DB:
    def __init__(self):
        self.conn = psycopg2.connect(dbname=configuration.Config.Database.name, user=configuration.Config.Database.user,
                                     password=configuration.Config.Database.password,
                                     host=configuration.Config.Database.host)
        self.conn.set_session(autocommit=True)

    def get_favorites(self):
        cursor = self.conn.cursor()
        cursor.execute(
            "SELECT user_id as usr,c2.city_code as origin,c.city_code as destination,outbound_date as out_date,f.cost as cost FROM favorite_flights ff JOIN flights f on f.flight_id = ff.flight_id join city c on f.destination_place_city_id = c.city_id join city c2 on c2.city_id = f.origin_place_city_id WHERE outbound_date>now()")
        r = cursor.fetchall()
        cursor.close()
        return r


class Telegram_DB:
    def __init__(self):
        self.conn = psycopg2.connect(dbname=configuration.Config.DatabaseTG.name,
                                     user=configuration.Config.DatabaseTG.user,
                                     password=configuration.Config.DatabaseTG.password,
                                     host=configuration.Config.DatabaseTG.host)
        self.conn.set_session(autocommit=True)

    def save_person(self, id, user_id: str):
        cursor = self.conn.cursor()
        cursor.execute(
            "INSERT INTO telegram_user (telegram_id,user_id) VALUES (%s,%s)",
            (id, user_id,))
        cursor.close()

    def get_person_by_id(self, id):
        cursor = self.conn.cursor()
        cursor.execute(
            "SELECT telegram_id FROM telegram_user WHERE user_id=%s", (id,))
        r = cursor.fetchone()
        cursor.close()
        return r
