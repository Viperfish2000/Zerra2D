#version 330

in vec2 pass_TextureCoords;
in vec2 pass_TextureCoords1;

layout (location = 0) out vec4 out_Color;
layout (location = 1) out vec4 out_BrightColor;

uniform sampler2D tileTexture;
uniform sampler2D tileGlowTexture;

void main() {
	out_Color = texture(tileTexture, pass_TextureCoords);
	out_BrightColor = texture(tileGlowTexture, pass_TextureCoords);
}
