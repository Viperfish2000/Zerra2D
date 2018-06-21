#version 330

in vec2 pass_TextureCoords;

out vec4 out_Color;

uniform sampler2D tileTexture;

void main() {
	out_Color = texture(tileTexture, pass_TextureCoords);
}
