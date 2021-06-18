import yaml

with open("configuration.yaml") as file:
    config: dict = yaml.safe_load(file)


class Config:
    name: str = config["name"]

    class Telegram:
        token: str = config["telegram"]["token"]

    class Database:
        name: str = config["database"]["name"]
        user: str = config["database"]["user"]
        password: str = config["database"]["password"]
        host: str = config["database"]["host"]

    class DatabaseTG:
        name: str = config["database_tg"]["name"]
        user: str = config["database_tg"]["user"]
        password: str = config["database_tg"]["password"]
        host: str = config["database_tg"]["host"]
