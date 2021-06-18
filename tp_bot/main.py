import asyncio
from aiogram import Bot, Dispatcher, executor
from aiogram.contrib.fsm_storage.memory import MemoryStorage
from aiogram.dispatcher import FSMContext
from aiogram.dispatcher.filters import BoundFilter
from aiogram.dispatcher.filters.state import State, StatesGroup
from aiogram.types import (
    Message as M,
    CallbackQuery as CQ,
    InlineKeyboardMarkup as IKB,
    InlineKeyboardButton as IKBB,
    ReplyKeyboardMarkup as KB,
    KeyboardButton as KBB,
    ContentType,
    ReplyKeyboardMarkup,
    InlineKeyboardMarkup,
    ReplyKeyboardRemove,
    ForceReply,
)
from loguru import logger
from classes import DB,Telegram_DB
import utils

token = "1868545953:AAGYVdFiTuH7wH3zi97u8pee-NnzdEloIeg"
storage = MemoryStorage()
bot = Bot(token=token)
dp = Dispatcher(bot=bot, storage=storage)
db = DB()
tg_db = Telegram_DB()
util = utils.Utils()


@dp.message_handler(commands=['start'])
async def start(m:M):
    tg_db.save_person(m.from_user.id, m.text.split()[-1])
    await bot.send_message(m.from_user.id,'{}, здравствуйте!\n\nВас приветствует чат-бот сервиса "AviaProject".\nТеперь вы подписаны на рассылку информации от нашего сервиса. Надеемся, что вы найдёте её полезной'.format(
                               m.from_user.full_name))


async def periodic(sleep_for):
    while True:
        await asyncio.sleep(sleep_for)
        for i in util.get_message():
            print(i[0][0])
            await bot.send_message(i[0][0],i[1])





if __name__ == "__main__":
    loop = asyncio.get_event_loop()
    loop.create_task(periodic(30))
    try:
        executor.start_polling(dp, loop=loop)
    except Exception as e:
        logger.error(e)