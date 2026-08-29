package com.existingeevee.moretcon;

import com.existingeevee.moretcon.client.actions.ClientAction.SentClientActionMessage;
import com.existingeevee.moretcon.effects.PotionBleeding.BleedingEffectMessage;
import com.existingeevee.moretcon.other.ExtendedAttackMessage;
import com.existingeevee.moretcon.other.LeftClickEvent.NotifyEmptyLeftClickMessage;
import com.existingeevee.moretcon.other.LeftClickEvent.NotifyEmptyLeftClickMessage.NotifyEmptyLeftClickMessageHandler;
import com.existingeevee.moretcon.other.fires.CustomFireHelper.SyncCustomFiresMessage;
import com.existingeevee.moretcon.other.pwt.net.SyncPersistantTickersMessage;
import com.existingeevee.moretcon.other.pwt.net.SyncPersistantTickersMessageHandler;
import com.existingeevee.moretcon.other.recoil.SendRecoilMessage;
import com.existingeevee.moretcon.traits.traits.Afterimage.AfterimageMessage;
import com.existingeevee.moretcon.world.generators.HelltopIslandsGenerator.HelltopStatusMessage;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

	public static final SimpleNetworkWrapper HANDLE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MODID);
	private static int i = 0;

	public static void init() {
		HANDLE.registerMessage(BleedingEffectMessage.class, BleedingEffectMessage.class, i++, Side.CLIENT);
		HANDLE.registerMessage(AfterimageMessage.class, AfterimageMessage.class, i++, Side.CLIENT);
		HANDLE.registerMessage(SyncCustomFiresMessage.class, SyncCustomFiresMessage.class, i++, Side.CLIENT);
		HANDLE.registerMessage(SentClientActionMessage.class, SentClientActionMessage.class, i++, Side.CLIENT);
		HANDLE.registerMessage(ExtendedAttackMessage.class, ExtendedAttackMessage.class, i++, Side.SERVER);
		HANDLE.registerMessage(HelltopStatusMessage.class, HelltopStatusMessage.class, i++, Side.CLIENT);
		HANDLE.registerMessage(SendRecoilMessage.class, SendRecoilMessage.class, i++, Side.CLIENT);		
		HANDLE.registerMessage(SyncPersistantTickersMessageHandler.class, SyncPersistantTickersMessage.class, i++, Side.CLIENT);
		HANDLE.registerMessage(NotifyEmptyLeftClickMessageHandler.class, NotifyEmptyLeftClickMessage.class, i++, Side.SERVER);
	}

}
