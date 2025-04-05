/*
package org.depromeet.spot.infrastructure.mixpanel.repository;


@Slf4j
@Component
@RequiredArgsConstructor
public class MixpanelRepositoryImpl implements MixpanelRepository {

    private final MixpanelProperties mixpanelProperties;

    // mixpanelEvent는 eventName(이 단위로 이벤트가 묶임)
    // distinctId는 사용자를 구분하는 데 사용됨.
    @Override
    public void eventTrack(MixpanelEvent mixpanelEvent) {
        try {

            // 믹스패널 이벤트 메시지 생성
            MessageBuilder messageBuilder = new MessageBuilder(mixpanelProperties.token());

            // 이벤트 생성
            JSONObject sentEvent =
                    messageBuilder.event(
                            mixpanelEvent.getDistinctId(),
                            mixpanelEvent.getMixpanelEventName().getValue(),
                            null);

            // 만든 여러 이벤트를 delivery
            ClientDelivery delivery = new ClientDelivery();
            delivery.addMessage(sentEvent);

            // Mixpanel로 데이터 전송
            MixpanelAPI mixpanel = new MixpanelAPI();
            mixpanel.deliver(delivery);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/
