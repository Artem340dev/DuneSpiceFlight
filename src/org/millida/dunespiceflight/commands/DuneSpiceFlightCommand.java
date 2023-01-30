package org.millida.dunespiceflight.commands;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.millida.dunespiceflight.DuneSpiceFlightPlugin;
import org.millida.dunespiceflight.objects.RocketEntity;
import org.millida.dunespiceflight.objects.RocketPlayerData;
import org.millida.dunespiceflight.objects.RocketWorld;
import org.millida.dunespiceflight.utils.*;

public class DuneSpiceFlightCommand extends AbstractCommand {
    @Override
    protected String getCommandName() {
        return "dunespiceflight";
    }

    @Override
    protected void help(CommandSender sender) {
        sender.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "/dsf panel - Панель управления космическим кораблём."));
        sender.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "/dsf destinations &7<название мира> &f- Список сохранённых точек в мире, куда может полететь корабль."));
        sender.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "/dsf flight &7<название мира> <номер слота> &f- Полететь в определённую точку."));
        sender.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "/dsf delete &7<название мира>&f all - Удалить всё точки в определённом мире."));
        sender.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "/dsf delete &7<название мира> <номер слота> &f- Удалить точку в определённом мире."));
        sender.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "/dsf add &f- Добавить точку, где стоит сейчас игрок."));
        sender.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "/dsf buy - Купить ракету."));
        sender.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "/dsf reload - Перезагрузка плагина"));
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 1) {
            if (args[0].equals("reload")) {
                player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "Выполняется перезагрузка..."));

                DuneSpiceFlightPlugin.getInstance().getMainConfiguration().onSave();
                DuneSpiceFlightPlugin.getInstance().getPlayersConfiguration().onSave();

                Bukkit.getScheduler().runTaskLater(DuneSpiceFlightPlugin.getInstance(), () -> {
                    DuneSpiceFlightPlugin.getInstance().getMainConfiguration().onLoad();
                    DuneSpiceFlightPlugin.getInstance().getPlayersConfiguration().onLoad();
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "Успешно!"));
                }, 20L*5);
                return;
            }

            if (args[0].equals("panel")) {
                RocketEntity rocketEntity = RocketEntity.getRocketEntityForCaptain(player);
                if (rocketEntity == null || rocketEntity.getCaptainArmorStand().getPassenger() == null || !rocketEntity.isCaptain(player)) {
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "&cОшибка! Вы не находитесь в космическом корабле!"));
                    return;
                }

                player.openInventory(InventoryManager.getInventory("Панель управления полётом").createInventory(player));
                return;
            }

            if (args[0].equals("buy")) {
                if (EconomyManager.getBalance(player) < RocketManager.getRocketPrice()) {
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "&cУ вас недостаточно денежных средств на счету!"));
                    return;
                }

                EconomyManager.withdrawPlayer(player, RocketManager.getRocketPrice());
                player.getInventory().addItem(RocketManager.getRocketItem());
                player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "Вы успешно приобрели космический корабль!"));
                return;
            }

            if (args[0].equals("add")) {
                RocketPlayerData data = RocketPlayerData.getRocketDataOrNull(player);
                if (data == null) data = new RocketPlayerData(player);

                RocketWorld rocketWorld = data.getWorld(player.getWorld().getName());
                if (rocketWorld == null) {
                    rocketWorld = new RocketWorld(player.getWorld());
                    data.addWorld(rocketWorld);
                }

                if (rocketWorld.getLocations().size() > RocketManager.getRocketMaxDestinations()) {
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "&cВы превысили лимит точек в этом мире!"));
                    return;
                }

                if (!TownyManager.isWilderness(player.getLocation()) && !TownyManager.isResident(player, player.getLocation())) {
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "&cВы не можете приземляться на территории чужого города!"));
                    return;
                }

                rocketWorld.addLocation(player.getLocation());
                player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "Вы успешно добавили новую точку прилёта ракеты!"));
                return;
            }
        }

        if (args.length == 2) {
            if (args[0].equals("destinations")) {
                String name = args[1];
                RocketPlayerData data = RocketPlayerData.getRocketDataOrNull(player);

                if (data == null || !data.worldExists(name)) {
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "&cОшибка! У вас нет точек в указанном мире."));
                    return;
                }

                RocketWorld rocketWorld = data.getWorld(name);
                player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "Список ваших точек в " + name + ":"));

                rocketWorld.getLocations().forEach((index, location) -> {
                    BaseComponent[] text = new MessageBuilder()
                            .add(DuneSpiceFlightPlugin.CHAT_PREFIX + "&a" + index + ". " + location.getBlockX() + "; " + location.getBlockY() + "; " + location.getBlockZ())
                            .add(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new MessageBuilder()
                                                                                .add("&b&lНажмите, чтобы попасть сюда.")
                                                                                .build()))
                            .add(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dsf flight " + name + " " + index))
                            .build();

                    player.spigot().sendMessage(text);
                });

                return;
            }
        }

        if (args.length == 3) {
            if (args[0].equals("flight")) {
                RocketEntity entity = RocketEntity.getRocketEntityForCaptain(player);
                RocketPlayerData rocketPlayerData = RocketPlayerData.getRocketDataOrNull(player);

                if (entity == null) {
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "&cВы не находитесь в космическом корабле!"));
                    return;
                }

                String worldName = args[1];
                World world = Bukkit.getWorld(worldName);
                if (world == null) {
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.ONBOARD_COMPUTER_PREFIX + "&cОшибка! Мир не найден!"));
                    return;
                }

                if (!PrototypeManager.isInteger(args[2])) {
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.ONBOARD_COMPUTER_PREFIX + "&cОшибка! Введённый вами номер слота не является числом."));
                    return;
                }

                int slot = Integer.parseInt(args[2]);
                if (rocketPlayerData == null || !rocketPlayerData.getWorld(worldName).locationExists(slot)) {
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.ONBOARD_COMPUTER_PREFIX + "&cТочка не найдена!"));
                    return;
                }

                Location location = rocketPlayerData.getWorld(worldName).getLocation(slot);
                entity.flyTo(location);
                return;
            }

            if (args[0].equals("delete")) {
                RocketPlayerData rocketPlayerData = RocketPlayerData.getRocketDataOrNull(player);
                String worldName = args[1];

                if (rocketPlayerData == null || !rocketPlayerData.worldExists(worldName)) {
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "&cОшибка! Мир не найден!"));
                    return;
                }

                if (args[2].equals("all")) {
                    rocketPlayerData.removeWorld(worldName);
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "Все точки в мире " + worldName + " были удалены."));
                    return;
                }

                if (!PrototypeManager.isInteger(args[2])) {
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "&cОшибка! Вы ввели неверный индекс."));
                    return;
                }

                RocketWorld rocketWorld = rocketPlayerData.getWorld(worldName);
                int index = Integer.parseInt(args[2]);

                if (!rocketWorld.locationExists(index)) {
                    player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "&cОшибка! Вы ввели неверный индекс."));
                    return;
                }

                rocketWorld.removeLocation(index);
                player.sendMessage(ChatUtil.parseColor(DuneSpiceFlightPlugin.CHAT_PREFIX + "Локация #" + index + " была успешно удалена!"));
                return;
            }
        }

        this.help(player);
    }
}