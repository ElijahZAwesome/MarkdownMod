package com.elijahzawesome.markdown.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.*;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.elijahzawesome.markdown.ChatColor;
import com.elijahzawesome.markdown.MarkdownParser;

@Mixin(ChatHud.class)
public class ChatMixin {

	@ModifyArg (
		method = "addMessage(Lnet/minecraft/text/Text;)V", 
		at = @At (value = "INVOKE", 
			target = "net/minecraft/client/gui/hud/ChatHud.addMessage(Lnet/minecraft/text/Text;I)V"
		), 
		index = 0
	)
	private Text addMessage(Text message) {
		String messageJson = Text.Serializer.toJson(message);
		
		messageJson = MarkdownParser.markdownToChat(
				messageJson, ChatColor.WHITE.toString());
		
		message = Text.Serializer.fromJson(messageJson);
		return message;
	}
}
